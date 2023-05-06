import { Router } from 'express';

import SMSController from '../../controllers/SMSController';
import authenticate from '../../middlewares/authenticate';
import requestLimiter from '../../middlewares/requestLimiter';
import SMSValidate from '../../validations/sms';

const smsRouter = Router();

smsRouter.post(
  '/',
  authenticate,
  SMSValidate.sms,
  requestLimiter,
  SMSController.send,
);

smsRouter.get(
  '/notifications',
  authenticate,
  SMSController.getNotifications,
);

export default smsRouter;
