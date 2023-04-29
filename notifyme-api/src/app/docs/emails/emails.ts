import responses from '../responses/default';

const emails = {
  '/api/emails': {
    post: {
      tags: ['emails'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Send email',
      consumes: ['application/json'],
      responses: {
        200: responses[200],
        401: responses[401],
      },
    },
  },
};

export default emails;
