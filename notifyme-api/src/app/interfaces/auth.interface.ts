import { Request as ExpressRequest } from 'express';

export interface DataStoredInToken {
  id: string;
  firstName: string;
  avatar: string;
  email: string;
  role: string;
}

export interface RequestWithUser extends ExpressRequest {
  user?: {
    id: string;
    firstName: string;
    avatar: string;
    email: string;
    role: string;
  };
}
