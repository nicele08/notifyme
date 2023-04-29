import responses from '../responses/default';

const users = {
  '/api/users': {
    get: {
      tags: ['Users'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Get all',
      consumes: ['application/json'],
      responses: {
        200: responses[200],
        401: responses[401],
      },
    },
  },
  '/api/users/{id}': {
    get: {
      tags: ['Users'],
      security: [
        {
          JWT: [],
        },
      ],
      parameters: [
        {
          name: 'id',
          in: 'path',
          description: 'User ID',
          required: true,
          type: 'integer',
        },
      ],
      summary: 'Get Single',
      consumes: ['application/json'],
      responses: {
        200: responses[200],
        401: responses[401],
      },
    },
  },
};

export default users;
