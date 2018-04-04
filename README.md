# gl-api
Grocery list API gateway

## Build with Docker

`docker build . --build-arg version=0.0.1-SNAPSHOT -t zmad5306/gl-api:latest`

`docker push zmad5306/gl-api:latest`

## Circle CI Build

https://circleci.com/gh/zmad5306/gl-api

# Authentication

## Login

If an HTTP response code `401 - Unauthorized` is returned from an API call the user needs to login. This is accomplished by sending a `POST` request to `/api/login` including a `username` and `password` parameter.

## Authorization

If an HTTP response code `403 - Forbidden` is returned from an API call the user does not have access to this resource.

## Logout

A session may be ended by sending a `GET` request to `/api/logout`

## Current User

The logged in user may be retrieved by sending a `GET` request to `/api/session/user`. The following JSON respon will be returned:

```
{
    "password": null,
    "username": "sue",
    "authorities": [
        {
            "authority": "ROLE_USER"
        }
    ],
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true
}
```

## CSRF Protection

All requests made (excluding logout) require the `XSRF-TOKEN` cookie. This cookie is returned from the API Gateway when the first `GET` request is made.

## In Memory Users (for testing)
| Username | Password |
| -------- | -------- |
| bob      | password |
| sue      | password |

# APIs

| Base Path    | Description                                              |
| ------------ | -------------------------------------------------------- |
| /api/dept/** | Departments API, see https://github.com/zmad5306/gl-dept |
| /api/list/** | List API, see https://github.com/zmad5306/gl-list        |
| /api/item/** | Item API, see https://github.com/zmad5306/gl-item        |

Postman library of all API endpoints: https://github.com/zmad5306/gl-api/blob/develop/GroceryList.postman_collection.json
