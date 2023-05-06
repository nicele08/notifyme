import { Request, Response, NextFunction } from 'express';
import joi from 'joi';

export default class EmailValidate {
  static async email(
    req: Request,
    res: Response,
    next: NextFunction,
  ) {
    const schema = joi.object().keys({
      fromName: joi.string().optional().allow(''),
      fromEmail: joi.string().email().optional().allow(''),
      to: joi.array().items(joi.string().email()).required(),
      subject: joi.string().required(),
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
