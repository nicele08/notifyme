import { Response, NextFunction } from 'express';
import { RequestWithUser } from '../interfaces/auth.interface';
import LimiterAPI from '../api/limiter';
import DB from '../database';
import { userRoles } from '../constants/user';

const requestLimiter = async (
  req: RequestWithUser,
  res: Response,
  next: NextFunction,
) => {
  try {
    const profile = await DB.Profile.findOne({
      where: {
        userId: req.user?.id,
      },
    });
    if (!profile) {
      return res.status(401).json({
        message: `You don't have api key, request api key on your profile`,
      });
    }
    const profileData = profile.toJSON();
    const limiterAPI = new LimiterAPI();
    const role =
      userRoles.find(role => role === req.user?.role) || 'client';
    await limiterAPI.createRequest({
      apiKey: profileData.apiKey,
      type: role.toUpperCase(),
    });
    next();
  } catch (error: any) {
    const status = error?.response?.status || 500;
    const message = error?.response?.data?.message || error.message;
    return res.status(status).json({
      message: message || 'Failed to create request',
    });
  }
};

export default requestLimiter;
