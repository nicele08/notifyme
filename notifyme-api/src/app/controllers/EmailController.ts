import { Request, Response } from 'express';
import { sendEmail } from '../utils/nodemailer';

export default class EmailController {
  static send = async (req: Request, res: Response) => {
    try {
      await sendEmail();
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
}
