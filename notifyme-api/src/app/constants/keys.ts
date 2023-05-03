import dotenv from 'dotenv';

dotenv.config();
const port = Number(process.env.PORT || 3000);
const hostUrl = process.env.HOST_URL || `http://localhost:${port}`;
const keys = {
  FRONT_END_URL: process.env.FRONT_END_URL || hostUrl,
  HOST_URL: hostUrl,
  PORT: port,
  JWT_SECRET: process.env.JWT_SECRET,
  JWT_EXPIRES_IN: process.env.JWT_EXPIRES_IN || '1h',

  LIMITER_API: process.env.LIMITER_API || 'http://localhost:8080',

  VONAGE_API_KEY: process.env.VONAGE_API_KEY,
  VONAGE_API_SECRET: process.env.VONAGE_API_SECRET,

  EMAIL_ADDRESS: process.env.EMAIL_ADDRESS,
  EMAIL_PASSWORD: process.env.EMAIL_PASSWORD,
  EMAIL_PORT: Number(process.env.EMAIL_PORT || 587),
  EMAIL_HOST: process.env.EMAIL_HOST,
};

export default keys;
