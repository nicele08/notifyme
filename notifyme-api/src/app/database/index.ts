import models, { connection } from './models';

const DB = {
  sequelize: connection, // connection instance (RAW queries)
  User: models.User,
  Profile: models.Profile,
  EmailNotification: models.EmailNotification,
  SMSNotification: models.SMSNotification,
};

export default DB;
