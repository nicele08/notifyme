const responses = {
  200: {
    description: 'Successful Operation',
  },
  201: {
    description: 'Successfully Created',
  },
  204: {
    description: 'No content',
  },
  301: {
    description: 'Moved Permanently redirect',
  },
  304: {
    description: 'Not modified',
  },
  400: {
    description: 'Bad request',
  },
  401: {
    description: 'Unauthorized',
  },
  403: {
    description: 'Forbidden',
  },
  404: {
    description: 'Not found',
  },
  405: {
    description: 'Method Not Allowed',
  },
  409: {
    description: 'Conflicts',
  },
  500: {
    description: 'Internal Server Error',
  },
  501: {
    description: 'Not Implemented',
  },
  502: {
    description: 'Bad Gateway',
  },
  503: {
    description: 'Service Unavailable',
  },
  504: {
    description: 'Gateway Timeout',
  },
};

export default responses;
