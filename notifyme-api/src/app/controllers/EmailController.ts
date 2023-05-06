import { Response } from 'express';
import { sendEmail } from '../utils/nodemailer';
import { RequestWithUser } from '../interfaces/auth.interface';

export default class EmailController {
  static send = async (req: RequestWithUser, res: Response) => {
    try {
      const { to, subject, text, fromEmail, fromName } = req.body;
      
      const name = fromName || req.user?.firstName;
      const from = fromEmail || req.user?.email;
      
      sendEmail({
        from: `${name} 🔔 <${from}>`,
        to: to.join(','),
        subject,
        text,
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
}
