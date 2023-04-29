import { Vonage } from '@vonage/server-sdk';
import keys from '../constants/keys';

const credentials: any = {
  apiKey: keys.VONAGE_API_KEY,
  apiSecret: keys.VONAGE_API_SECRET,
};

const vonage = new Vonage(credentials);

export default vonage;
