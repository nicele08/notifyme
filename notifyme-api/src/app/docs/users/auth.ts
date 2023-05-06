import responses from '../responses/default';

const auth = {
  '/api/auth/login': {
    post: {
      tags: ['Auth'],
      security: [],
      summary: 'Signin to NotifyMe',
      parameters: [
        {
          in: 'body',
          name: 'body',
          required: true,
          schema: {
            type: 'object',
            properties: {
              email: {
                type: 'string',
                example: '',
              },
              password: {
                type: 'string',
                example: '',
              },
            },
          },
        },
      ],
      consumes: ['application/json'],
      responses: {
        200: responses[200],
        400: responses[400],
      },
    },
  },
  '/api/auth/signup': {
    post: {
      tags: ['Auth'],
      security: [],
      summary: 'Register',
      parameters: [
        {
          in: 'body',
          name: 'body',
          required: true,
          schema: {
            type: 'object',
            properties: {
              firstName: {
                type: 'string',
                example: '',
              },
              lastName: {
                type: 'string',
                example: '',
              },
              email: {
                type: 'string',
                example: '',
              },
              password: {
                type: 'string',
                example: '',
              },
            },
          },
        },
      ],
      consumes: ['application/json'],
      responses: {
        201: responses[200],
        400: responses[400],
      },
    },
  },
  '/api/auth/logout': {
    post: {
      tags: ['Auth'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Logout',
      parameters: [],
      consumes: ['application/json'],
      responses: {
        200: responses[200],
      },
    },
  },
  '/api/auth/forgot-password': {
    post: {
      tags: ['Auth'],
      security: [],
      summary: 'forget password',
      parameters: [
        {
          in: 'body',
          name: 'body',
          required: true,
          schema: {
            type: 'object',
            properties: {
              email: {
                type: 'string',
                example: '',
              },
            },
          },
        },
      ],
      consumes: ['application/json'],
      responses: {
        200: responses[200],
      },
    },
  },
  '/api/auth/reset-password': {
    patch: {
      tags: ['Auth'],
      security: [],
      summary: 'reset password',
      parameters: [
        {
          in: 'body',
          name: 'body',
          required: true,
          schema: {
            type: 'object',
            properties: {
              password: {
                type: 'string',
                example: '',
              },
              token: {
                type: 'string',
                example: '',
              },
            },
          },
        },
      ],
      consumes: ['application/json'],
      responses: {
        200: responses[200],
      },
    },
  },
};

export default auth;
