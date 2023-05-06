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
      description: 'Get API Key for the logged in user',
      consumes: ['application/json'],
      responses: {
        200: responses[200],
        401: responses[401],
        429: responses[429],
      },
    },
  },
};

export default profile;
