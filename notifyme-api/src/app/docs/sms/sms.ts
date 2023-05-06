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
                example: '250...',
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

  '/api/sms/notifications': {
    get: {
      tags: ['Notifications'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Get sms notifications',
      description: 'Get sms notifications for the logged in user',
      consumes: ['application/json'],

      responses: {
        200: responses[200],
        401: responses[401],
      },
    },
  },
};

export default sms;
