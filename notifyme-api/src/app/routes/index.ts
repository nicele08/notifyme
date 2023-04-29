import { Router } from 'express';
import swaggerUi from 'swagger-ui-express';
import appDocs from '../docs';
import emailRouter from './api/email';

const appRouter = Router();

appRouter.use('/api-docs', swaggerUi.serve, swaggerUi.setup(appDocs));

appRouter.use('/api/emails', emailRouter);

export default appRouter;
