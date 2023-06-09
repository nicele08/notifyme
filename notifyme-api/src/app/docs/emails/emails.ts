import responses from '../responses/default';

const emails = {
  '/api/emails': {
    post: {
      tags: ['Emails'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Send email',
      consumes: ['application/json'],
      parameters: [
        {
          in: 'body',
          name: 'body',
          description: 'Email object',
          required: true,
          schema: {
            type: 'object',
            properties: {
              fromName: {
                type: 'string',
                example: '',
                description:
                  'Name to send from (Optional if user is logged in)',
              },
              fromEmail: {
                type: 'string',
                example: '',
                description:
                  'Email address to send from (Optional if user is logged in)',
              },
              to: {
                type: 'array',
                items: {
                  type: 'string',
                  example: '',
                  required: true,
                },
                description: 'Email addresses to send to',
              },
              subject: {
                type: 'string',
                example: '',
                required: true,
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

  '/api/emails/notifications': {
    get: {
      tags: ['Notifications'],
      security: [
        {
          JWT: [],
        },
      ],
      summary: 'Get email notifications',
      description: 'Get email notifications for the logged in user',
      consumes: ['application/json'],

      responses: {
        200: responses[200],
        401: responses[401],
      },
    },
  },
};

export default emails;
