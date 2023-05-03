import { Router } from 'express';
import AuthController from '../../controllers/AuthController';
import AuthValidate from '../../validations/auth';
import authenticate from '../../middlewares/authenticate';

const authRouter = Router();

authRouter.post(
  '/signup',
  AuthValidate.signup,
  AuthController.signUp,
);
authRouter.post('/login', AuthValidate.login, AuthController.logIn);
authRouter.post('/logout', authenticate, AuthController.logOut);

authRouter.post(
  '/forgot-password',
  AuthValidate.forgetPassword,
  AuthController.forgettingPassword,
);

authRouter.patch(
  '/reset-password',
  AuthValidate.resetPassword,
  AuthController.resetingPassword,
);

authRouter.post(
  '/verify',
  AuthValidate.verify,
  AuthController.confirmAccount,
);

export default authRouter;
