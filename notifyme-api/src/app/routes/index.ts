import { Router } from 'express';
import swaggerUi from 'swagger-ui-express';
import appDocs from '../docs';
import emailRouter from './api/email';
import authRouter from './api/auth';

const appRouter = Router();

appRouter.use('/api-docs', swaggerUi.serve, swaggerUi.setup(appDocs));

appRouter.use('/api/emails', emailRouter);

appRouter.use('/api/auth', authRouter);

export default appRouter;
