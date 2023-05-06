import crypto from 'crypto';

const generateApiKey = () => {
  const apiKey = crypto.randomBytes(16).toString('hex');
  return apiKey;
};

export default generateApiKey;
