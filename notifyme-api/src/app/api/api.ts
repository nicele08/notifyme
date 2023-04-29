import axios from 'axios';
import keys from '../constants/keys';

const API = axios.create({
  baseURL: keys.SERVICE_API,
});

export default API;
