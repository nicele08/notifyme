import dotenv from 'dotenv';

dotenv.config();

const keys = {
  PORT: Number(process.env.PORT || 3000),
  JWT_SECRET: process.env.JWT_SECRET,
  JWT_EXPIRES_IN: process.env.JWT_EXPIRES_IN || '1h',

  LIMITER_API: process.env.LIMITER_API || 'http://localhost:8080',

  // Vonage
  VONAGE_API_KEY: process.env.VONAGE_API_KEY,
  VONAGE_API_SECRET: process.env.VONAGE_API_SECRET
};

export default keys;
