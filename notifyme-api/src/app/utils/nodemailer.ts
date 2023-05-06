import nodemailer from 'nodemailer';
import keys from '../constants/keys';

interface EmailOptions {
  from: string;
  to: string;
  subject: string;
  text?: string;
  html?: string;
}
export const sendEmail = async (options: EmailOptions) => {
  try {
    // create reusable transporter object using the default SMTP transport
    const transporter = nodemailer.createTransport({
      host: keys.EMAIL_HOST,
      port: keys.EMAIL_PORT,
      secure: keys.PORT === 465, // true for 465, false for other ports
      auth: {
        user: keys.EMAIL_ADDRESS,
        pass: keys.EMAIL_PASSWORD,
      },
    });

    // send mail with defined transport object
    const info = await transporter.sendMail({
      from: options.from, // sender address
      to: options.to, // list of receivers
      subject: options.subject, // Subject line
      text: options.text, // plain text body
      html: options.html, // html body
    });
    return info;
  } catch (error) {
    console.log(error);
    return null;
  }
};
