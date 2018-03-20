# gl-api
Grocery list API gateway

## Build with Docker

`docker build . --build-arg version=0.0.1-SNAPSHOT -t gl-api:latest`

#Authentication

## Login

If an HTTP response code `401 - Unauthorized` is returned from an API call the user needs to login. This is accomplished by sending a `POST` request to `/api/login` including a `username` and `password` parameter.

## Authorization

If an HTTP response code `403 - Forbidden` is returned from an API call the user does not have access to this resource.

## Logout

A session may be ended by sending a `GET` request to `/api/logout`

## CSRF Protection

All requests made (excluding logout) require the `XSRF-TOKEN` cookie. This cookie is returned from the API Gateway when the first `GET` request is made.

## In Memory Users (for testing)
| Username | Password |
| -------- | -------- |
| bob      | password |
| sue      | password |
