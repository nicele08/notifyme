import { NextFunction, Request, Response } from 'express';
import { compare, hash } from 'bcryptjs';
import DB from '../database';
import { verify, sign, decode } from 'jsonwebtoken';
import EmailTemplate from '../templates/email';
import { HttpException } from '../exceptions/HttpException';
import {
  DataStoredInToken,
  RequestWithUser,
} from '../interfaces/auth.interface';
import keys from '../constants/keys';
import { sendEmail } from '../utils/nodemailer';

class AuthController {
  static signUp = async (req: Request, res: Response) => {
    try {
      const userData = req.body;
      userData.email = userData.email.toLowerCase().trim();

      const findUser = await DB.User.findOne({
        where: {
          email: userData.email,
        },
      });
      if (findUser)
        throw new HttpException(
          409,
          `This email ${userData.email} already exists`,
        );

      const hashedPassword = await hash(userData.password, 10);
      const createUserData = await DB.User.create({
        ...userData,
        password: hashedPassword,
      });

      const { password, ...signUpUserData } = createUserData.toJSON();

      const token = AuthController.createToken(
        signUpUserData.id,
        signUpUserData.email,
        signUpUserData.role,
        signUpUserData.firstName,
      );

      const message = EmailTemplate.verifyAccount(
        signUpUserData.firstName,
        token,
      );

      const subject = 'Account Verification';
      const mailOptions = {
        from: `NotifyMe 👻 <${keys.EMAIL_ADDRESS}>`,
        to: signUpUserData.email,
        subject,
        html: message,
      };
      sendEmail(mailOptions).catch(error => {
        console.log('Error sending email: ', error.message);
      });

      res.status(201).json({
        message: `Dear ${signUpUserData.firstName}, check your email to verify your account`,
        data: signUpUserData,
      });
    } catch (error: any) {
      res.status(error?.status || 500).json({
        message: error?.message || 'something went wrong',
      });
    }
  };

  static logIn = async (
    req: Request,
    res: Response,
    next: NextFunction,
  ) => {
    try {
      const userData: any = req.body;
      userData.email = userData.email.toLowerCase().trim();

      const findUser = await DB.User.findOne({
        where: { email: userData.email },
      });
      if (!findUser)
        throw new HttpException(
          409,
          `Invalid login credentials. Please check your email and password and try again.`,
        );

      const findUserData = findUser.toJSON();

      const isPasswordMatching: boolean = await compare(
        userData.password,
        findUserData.password,
      );
      if (!isPasswordMatching)
        throw new HttpException(
          409,
          'Invalid login credentials. Please check your email and password and try again.',
        );

      // if (!findUserData.verified)
      //   throw new HttpException(
      //     400,
      //     `This email ${userData.email} was not verified, please check your email and follow instructions.`,
      //   );

      const tokenData = AuthController.createToken(
        findUserData.id,
        findUserData.email,
        findUserData.role,
        findUserData.firstName,
      );
      const cookie = AuthController.createCookie(tokenData);

      res.setHeader('Set-Cookie', [cookie]);
      res
        .status(200)
        .json({ data: findUser, tokenData, message: 'login' });
    } catch (error: any) {
      res.status(error?.status || 500).json({
        message: error?.message || 'something went wrong',
      });
    }
  };

  static logOut = async (req: RequestWithUser, res: Response) => {
    try {
      const userData = req.user;
      let findUser = await DB.User.findOne({
        where: { email: userData?.email, id: userData?.id },
      });
      if (!findUser)
        throw new HttpException(409, "User doesn't exist");

      findUser = await findUser.update({ status: 0 });

      res.setHeader('Set-Cookie', ['Authorization=; Max-age=0']);
      res.status(200).json({ data: findUser, message: 'logout' });
    } catch (error: any) {
      res.status(error?.status || 500).json({
        message: error?.message || 'something went wrong',
      });
    }
  };

  static createToken(
    id: string,
    email: string,
    role: string,
    firstName: string,
  ) {
    const dataStoredInToken = {
      id,
      email,
      role,
      firstName,
    };
    const secretKey = keys.JWT_SECRET as string;
    const expiresIn: number | string = keys.JWT_EXPIRES_IN;

    return sign(dataStoredInToken, secretKey, { expiresIn });
  }

  static createCookie(tokenData: any): string {
    return `Authorization=${tokenData.token}; HttpOnly; Max-Age=${tokenData.expiresIn};`;
  }

  static decode = (token: string) => {
    const payload = verify(token, keys.JWT_SECRET as string);
    return payload;
  };

  static async forgettingPassword(req: Request, res: Response) {
    let { email } = req.body;
    try {
      email = email.toLowerCase().trim();
      const user = await DB.User.findOne({
        where: { email },
      });
      if (!user) {
        throw new HttpException(409, 'user not found, signup');
      }

      const userData = user.toJSON();

      const token = AuthController.createToken(
        userData.id,
        userData.email,
        userData.role,
        userData.firstName,
      );
      const message = EmailTemplate.forgetPassword(token);
      const subject = 'Reset Password';
      const mailOptions = {
        from: `NotifyMe 👻 <${keys.EMAIL_ADDRESS}>`,
        to: userData.email,
        subject,
        html: message,
      };
      sendEmail(mailOptions).catch(error => {
        console.log('Error sending email: ', error.message);
      });
      res.status(200).json({
        message: 'check your email',
      });
    } catch (error: any) {
      res.status(error?.status || 500).json({
        message: error?.message || 'something went wrong',
      });
    }
  }

  static async resetingPassword(req: Request, res: Response) {
    const { password, token } = req.body;
    try {
      const decoded: any = AuthController.decode(token);
      const { id } = decoded;
      let user = await DB.User.findByPk(id);
      if (!user) {
        throw new HttpException(409, 'User not found, signup');
      }
      const hashPassword = await hash(password, 10);

      user.set({ password: hashPassword });
      user = await user.save();
      res.status(201).json({
        message: 'Password updated successfully',
      });
    } catch (error: any) {
      res.status(error?.status || 500).json({
        message: error?.message || 'something went wrong',
      });
    }
  }

  static async confirmAccount(req: Request, res: Response) {
    const { token } = req.body;
    try {
      const decodedToken = decode(token) as DataStoredInToken;
      if (!decodedToken.id) {
        throw new HttpException(
          400,
          'Your verification link may have expired.',
        );
      }
      let user = await DB.User.findByPk(decodedToken.id);
      if (!user) {
        throw new HttpException(401, 'user not found, signup first');
      }

      const userData = user.toJSON();

      if (userData.verified) {
        res.status(200).json({
          message: 'User verified, login',
          data: user,
        });
      } else {
        user = await user.update({ verified: true });

        res.status(200).json({
          message: 'Account verified successfully',
          data: user,
        });
      }
    } catch (err: any) {
      res.status(err?.status || 500).json({
        message: err?.message || 'Something went wrong',
      });
    }
  }
}

export default AuthController;
