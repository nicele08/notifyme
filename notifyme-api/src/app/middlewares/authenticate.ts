import { Response, NextFunction } from 'express';
import jwt from 'jsonwebtoken';
import keys from '../constants/keys';
import {
  DataStoredInToken,
  RequestWithUser,
} from '../interfaces/auth.interface';

const authenticate = (
  req: RequestWithUser,
  res: Response,
  next: NextFunction,
) => {
  try {
    const token = req.headers.authorization
      ? req.headers.authorization.split(' ')[1]
      : null;
    if (!token) {
      return res
        .status(401)
        .json({ message: 'Authentication required.' });
    }
    const payload = jwt.verify(
      token,
      keys.JWT_SECRET as string,
    ) as DataStoredInToken;

    if (!payload || !payload.id) {
      return res.status(401).json({
        message: 'Invalid token.',
      });
    }
    req.user = payload;
    next();
  } catch (error: any) {
    return res.status(500).json({
      message: 'Something went wrong',
      error: error.message,
    });
  }
};

export default authenticate;
