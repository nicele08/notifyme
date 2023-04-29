const paths = {
  '/': {
    get: {
      tags: ['Default'],
      summary: 'Default message on server',
      operationId: '',
      requestBody: {
        description: 'default router should return message',
        content: {
          'application/json': {
            schema: {},
          },
          'application/xml': {
            schema: {},
          },
        },
        required: false,
      },
      responses: {
        '200': {
          description: 'Message of successful request',
          content: {},
        },
      },
      'x-codegen-request-body-name': 'body',
    },
  },
};

export default paths;
