import responses from '../responses/default';

const profile = {
  '/api/profile/api-key': {
    post: {
      tags: ['Profile'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Get API Key',
      consumes: ['application/json'],
      responses: {
        200: responses[200],
        401: responses[401],
      },
    },
  },
};

export default profile;
