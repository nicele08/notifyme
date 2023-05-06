import { Request, Response, NextFunction } from 'express';
import joi from 'joi';

export default class SMSValidate {
  static async sms(
    req: Request,
    res: Response,
    next: NextFunction,
  ) {
    const schema = joi.object().keys({
      from: joi.string().optional().allow(''),
      to: joi.string().required(),
      text: joi.string().required(),
    });
    const { error } = schema.validate(req.body);
    if (error) {
      return res.status(400).json({
        message: error.details[0].message.replace(/"/g, ''),
      });
    }
    return next();
  }
}
