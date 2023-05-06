import { Response } from 'express';
import { RequestWithUser } from '../interfaces/auth.interface';
import DB from '../database';
import generateApiKey from '../api/helpers/generateApiKey';
import LimiterAPI from '../api/limiter';

export default class ProfileController {
  static requestAPIKey = async (
    req: RequestWithUser,
    res: Response,
  ) => {
    const limiterAPI = new LimiterAPI();
    try {
      let profile = await DB.Profile.findOne({
        where: {
          userId: req.user?.id,
        },
      });

      if (!profile) {
        const data = await limiterAPI.createClient({
          apiKey: generateApiKey(),
          email: req.user?.email,
          name: req.user?.firstName,
        });

        if (!data) {
          return res.status(500).json({
            message: 'Failed to get api key',
          });
        }

        profile = await DB.Profile.create({
          userId: req.user?.id,
          apiKey: data.apiKey,
        });
      }

      const profileData = profile.toJSON();

      limiterAPI.getClient(profileData.apiKey).catch(() => {
        limiterAPI
          .createClient({
            apiKey: profileData.apiKey,
            email: req.user?.email,
            name: req.user?.firstName,
          })
          .catch(error => {
            console.log(error);
          });
      });
      return res.status(200).json({
        apiKey: profileData.apiKey,
      });
    } catch (error: any) {
      const status = error?.response?.status || 500;
      const message = error?.response?.data?.message || error.message;
      return res.status(status).json({
        message: message || 'Failed to retrieve api key',
        error: error.message,
      });
    }
  };
}
