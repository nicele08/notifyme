import { Router } from 'express';
import ProfileController from '../../controllers/ProfileController';
import authenticate from '../../middlewares/authenticate';

const profileRouter = Router();

profileRouter.post(
  '/api-key',
  authenticate,
  ProfileController.requestAPIKey,
);

export default profileRouter;
