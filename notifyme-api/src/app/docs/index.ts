import defaultPaths from './default/default.paths';
import emails from './emails/emails';
import auth from './users/auth';
import profile from './users/profile';
import users from './users/users';

const paths = {
  ...defaultPaths,
  ...auth,
  ...profile,
  ...emails,
  ...users,
};

const appDocs = {
  swagger: '2.0',
  info: {
    version: '1.0.0.',
    title: 'NotifyMe API Docs',
    description: '',
  },
  basePath: '/',
  schemes: ['http', 'https'],
  securityDefinitions: {
    JWT: {
      type: 'apiKey',
      name: 'Authorization',
      in: 'header',
    },
  },
  tags: [
    {
      name: 'NotifyMe API Docs',
    },
  ],
  consumes: ['application/json'],
  produces: ['application/json'],
  paths,
};
export default appDocs;
