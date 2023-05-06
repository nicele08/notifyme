# Limiter-API and Notifyme-API

This project contains two API projects: Limiter-API and Notifyme-API.

## Limiter-API

Limiter-API is a Spring Boot project that provides a rate limiting feature for APIs. It uses PostgreSQL to store client request limits and Redis to store request counts. The project also uses AOP to intercept incoming API requests and applies rate limiting before forwarding them to the API controller.

### Prerequisites

- Java 17
- PostgreSQL
- Redis

### Configuration

The following environment variables should be set:

- `spring.datasource.url`: The URL for the PostgreSQL database.
- `spring.datasource.username`: The username for the PostgreSQL database.
- `spring.datasource.password`: The password for the PostgreSQL database.
- `spring.redis.host`: The host for the Redis database.
- `spring.redis.port`: The port for the Redis database.
- `max-requests-per-minute`: The maximum number of requests per minute for entire system.
- `max-requests-per-time-window`: The maximum number of requests per time window for each client.
- `max-requests-per-month`: The maximum number of requests per month for each client.
- `time-window-in-hours`: The time window in hours for each client.

### How to Run

To run the project, execute the following command:

#### Install dependencies

Move to the `limiter-api` project directory and check if you have maven installed

```shell
mvn -v
```

```shell
mvn clean install
```

#### Run the project

```shell
mvn spring-boot:run
```

### API Endpoints

Open the following URL in your browser to see the Swagger UI:

```shell
http://localhost:8080/swagger-ui.html
```

## Notifyme-API

Notifyme-API is an Express.js project that provides a notification service API. It uses PostgreSQL to store notifications and Sequelize as an ORM. The project is written in TypeScript.

### Prerequisites

- Node.js 16 or higher
- PostgreSQL

### Configuration

The following environment variables should be set and can be found in the `.env.example` file inside the `notifyme-api` project directory:

- `LIMITER_API`: The URL for the Limiter-API.
- `DATABASE_URL`: The URL for the PostgreSQL database.
- `JWT_SECRET`: The secret key for JWT.
- `EMAIL_HOST`: The host for the email server.
- `EMAIL_PORT`: The port for the email server.
- `EMAIL_ADDRESS`: The email address for the email server.
- `EMAIL_PASSWORD`: The password for the email server.
- `VONAGE_API_KEY`: The API key for Vonage.
- `VONAGE_API_SECRET`: The API secret for Vonage.

### How to Run

To run the project, execute the following commands:

#### Install dependencies

Move to the `notifyme-api` project directory and check if you have Node.js and Yarn installed

```shell
node -v
```

```shell
yarn -v
```

```shell
yarn install
```

#### Build the project

```shell
yarn build
```

#### Run migrations

```shell
yarn db:migrate
```

#### Run the project

```shell
yarn start
```

### API Endpoints

Open the following URL in your browser to see the Swagger UI:

```shell
http://localhost:3000/api-docs
```

## How to Run with Docker Compose

To run the project with Docker Compose, execute the following commands:

```shell
docker-compose build
```

```shell
docker-compose up
```

## Author

[Celestin Niyindagiriye](https://github.com/nicele08)
