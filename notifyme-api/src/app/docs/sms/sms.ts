import responses from '../responses/default';

const sms = {
  '/api/sms': {
    post: {
      tags: ['SMS'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Send sms',
      consumes: ['application/json'],
      parameters: [
        {
          in: 'body',
          name: 'body',
          description: 'sms object',
          required: true,
          schema: {
            type: 'object',
            properties: {
              from: {
                type: 'string',
                example: '',
                description:
                  'Name to send from (Optional if user is logged in)',
              },
              to: {
                type: 'string',
                example: '',
                required: true,
                description: 'Phone number to send to',
              },
              text: {
                type: 'string',
                example: '',
                required: true,
              },
            },
          },
        },
      ],

      responses: {
        200: responses[200],
        401: responses[401],
        404: responses[404],
        429: responses[429],
      },
    },
  },
};

export default sms;
