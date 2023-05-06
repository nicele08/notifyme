import { Router } from 'express';
import swaggerUi from 'swagger-ui-express';
import appDocs from '../docs';
import emailRouter from './api/email';
import authRouter from './api/auth';
import profileRouter from './api/profile';
import smsRouter from './api/sms';

const appRouter = Router();

appRouter.use('/api-docs', swaggerUi.serve, swaggerUi.setup(appDocs));

appRouter.use('/api/emails', emailRouter);

appRouter.use('/api/auth', authRouter);

appRouter.use('/api/profile', profileRouter);

appRouter.use('/api/sms', smsRouter);

export default appRouter;
