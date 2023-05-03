import { Router } from 'express';

import EmailController from '../../controllers/EmailController';
import authenticate from '../../middlewares/authenticate';

const emailRouter = Router();

emailRouter.post('/', authenticate, EmailController.send);

export default emailRouter;
