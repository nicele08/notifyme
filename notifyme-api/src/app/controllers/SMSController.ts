import { Response } from 'express';
import { RequestWithUser } from '../interfaces/auth.interface';
import vonage from '../utils/vonage';

export default class SMSController {
  static send = async (req: RequestWithUser, res: Response) => {
    try {
      const { to, text, from } = req.body;

      await vonage.sms.send({
        from: from || req.user?.firstName,
        to,
        text,
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
}
