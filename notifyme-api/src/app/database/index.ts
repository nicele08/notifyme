import models, { connection } from './models';

const DB = {
  sequelize: connection, // connection instance (RAW queries)
  User: models.User,
  Profile: models.Profile,
};

export default DB;
