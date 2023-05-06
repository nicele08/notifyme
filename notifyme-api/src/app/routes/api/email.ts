import { Router } from 'express';

import EmailController from '../../controllers/EmailController';
import authenticate from '../../middlewares/authenticate';
import requestLimiter from '../../middlewares/requestLimiter';
import EmailValidate from '../../validations/email';

const emailRouter = Router();

emailRouter.post(
  '/',
  authenticate,
  EmailValidate.email,
  requestLimiter,
  EmailController.send,
);

export default emailRouter;
