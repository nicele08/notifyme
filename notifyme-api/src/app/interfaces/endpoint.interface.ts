export interface Endpoint {
  path: string;
  method: 'post' | 'get' | 'put' | 'delete';
  requiresAuth: boolean;
  requireIdentifier?: boolean;
  schema?: any;
  isWs?: boolean;
  isLogin?: boolean;
}
