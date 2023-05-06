import { Response } from 'express';
import { sendEmail } from '../utils/nodemailer';
import { RequestWithUser } from '../interfaces/auth.interface';
import DB from '../database';

export default class EmailController {
  static send = async (req: RequestWithUser, res: Response) => {
    try {
      const { to, subject, text, fromEmail, fromName } = req.body;

      const name = fromName || req.user?.firstName;
      const from = fromEmail || req.user?.email;
      const emailNotification = {
        fromName: name,
        fromEmail: from,
        to,
        subject,
        text,
        status: 'pending',
        ownerId: req.user?.id,
      };
      sendEmail({
        from: `${name} 🔔 <${from}>`,
        to: to.join(','),
        subject,
        text,
      })
        .then(() => {
          emailNotification.status = 'sent';
        })
        .catch(() => {
          emailNotification.status = 'failed';
        })
        .finally(() => {
          DB.EmailNotification.create(emailNotification).catch(
            error => {
              console.log(error);
            },
          );
        });
      res.status(200).json({
        message: 'Email sent successfully',
      });
    } catch (error: any) {
      res.status(500).json({
        message: 'Failed to send email',
        error: error.message,
      });
    }
  };

  static getNotifications = async (
    req: RequestWithUser,
    res: Response,
  ) => {
    try {
      const notifications = await DB.EmailNotification.findAll({
        where: {
          ownerId: req.user?.id,
        },
      });
      res.status(200).json({
        message: 'Email notifications retrieved successfully',
        notifications,
      });
    } catch (error: any) {
      res.status(500).json({
        message: 'Failed to retrieve email notifications',
        error: error.message,
      });
    }
  };
}
