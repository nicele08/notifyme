import fs from 'fs';
import path from 'path';

import { Sequelize, DataTypes } from 'sequelize';

import { IModel } from '../../interfaces/model.interface';

const basename = path.basename(__filename);
const env = process.env.NODE_ENV || 'development';
const config = require(`${__dirname}/../config/db.config.js`)[env];
const db: Record<string, IModel> = {};

let sequelize: Sequelize;
if (config.use_env_variable) {
  sequelize = new Sequelize(
    process.env[config.use_env_variable] as string,
    config,
  );
} else {
  sequelize = new Sequelize(
    config.database,
    config.username,
    config.password,
    config,
  );
}

sequelize
  .authenticate()
  .then(() => {
    console.log(
      '✅ Database connection has been established successfully.',
    );
  })
  .catch(error => {
    console.error(
      '❌ Unable to connect to the database:',
      error.message,
    );
  });

fs.readdirSync(`${__dirname}/definitions`)
  .filter(
    file =>
      file.indexOf('.') !== 0 &&
      file !== basename &&
      file.slice(-3) === '.js',
  )
  .forEach(file => {
    const model = require(path.join(
      `${__dirname}/definitions`,
      file,
    ))(sequelize, DataTypes);
    db[model.name] = model;
  });

Object.keys(db).forEach(modelName => {
  if (typeof db[modelName].associate === 'function') {
    db[modelName].associate(db);
  }
});

export const connection = sequelize;

export default db;
