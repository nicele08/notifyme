const dotenv = require('dotenv');

dotenv.config();

module.exports = {
  development: {
    // username: process.env.DB_USER,
    // password: process.env.DB_PASSWORD,
    // database: process.env.DB_NAME,
    // host: process.env.DB_HOST,
    use_env_variable: 'DATABASE_URL',
    dialect: 'postgres',
    port: process.env.DB_PORT,
    seederStorage: 'sequelize',
  },
  test: {
    // username: process.env.DB_USER,
    // password: process.env.DB_PASSWORD,
    // database: process.env.DB_NAME_TEST,
    // host: process.env.DB_HOST,
    use_env_variable: 'DATABASE_URL',
    dialect: 'postgres',
    port: process.env.DB_PORT,
    seederStorage: 'sequelize',
    logging: false,
  },
  production: {
    use_env_variable: 'DATABASE_URL',
    dialect: 'postgres',
    port: process.env.DB_PORT,
  },
};
