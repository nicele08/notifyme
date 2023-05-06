import { Response } from 'express';
import { RequestWithUser } from '../interfaces/auth.interface';
import vonage from '../utils/vonage';
import DB from '../database';

export default class SMSController {
  static send = async (req: RequestWithUser, res: Response) => {
    try {
      const { to, text, from } = req.body;

      const smsNotification = {
        from,
        to,
        text,
        status: 'pending',
        ownerId: req.user?.id,
      };

      vonage.sms
        .send({
          from: from || req.user?.firstName,
          to,
          text,
        })
        .then(() => {
          smsNotification.status = 'sent';
        })
        .catch(() => {
          smsNotification.status = 'failed';
        })
        .finally(() => {
          DB.SMSNotification.create(smsNotification).catch(error => {
            console.log(error);
          });
        });
      res.status(200).json({
        message: 'SMS sent successfully',
      });
    } catch (error: any) {
      res.status(500).json({
        message: 'Failed to send SMS',
        error: error.message,
      });
    }
  };

  static getNotifications = async (
    req: RequestWithUser,
    res: Response,
  ) => {
    try {
      const notifications = await DB.SMSNotification.findAll({
        where: {
          ownerId: req.user?.id,
        },
      });
      res.status(200).json({
        message: 'SMS notifications retrieved successfully',
        notifications,
      });
    } catch (error: any) {
      res.status(500).json({
        message: 'Failed to retrieve SMS notifications',
        error: error.message,
      });
    }
  };
}
