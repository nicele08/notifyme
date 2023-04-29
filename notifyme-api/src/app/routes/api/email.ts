import { Router } from 'express';

import EmailController from '../../controllers/EmailController';

const emailRouter = Router();

emailRouter.post('/', EmailController.send);

export default emailRouter;
