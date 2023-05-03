import { Response, NextFunction } from 'express';
import { RequestWithUser } from '../interfaces/auth.interface';

const authorize =
  (roles: string[]) =>
  (req: RequestWithUser, res: Response, next: NextFunction) => {
    if (!req.user) {
      return res
        .status(401)
        .json({ message: 'Authentication required.' });
    }
    if (!roles.includes(req.user.role)) {
      return res
        .status(403)
        .json({ message: 'Unauthorized access.' });
    }
    next();
  };

export default authorize;
