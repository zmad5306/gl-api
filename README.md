# gl-api
Grocery list API gateway

### Build with Docker

`docker build . --build-arg version=0.0.1-SNAPSHOT -t zmad5306/gl-api:latest`

`docker push zmad5306/gl-api:latest`

### Circle CI Build

https://circleci.com/gh/zmad5306/gl-api

## Authentication

### Login

If an HTTP response code `401 - Unauthorized` is returned from an API call the user needs to login. This is accomplished by sending a `POST` request to `/api/login` including a `username` and `password` parameter.

### Authorization

If an HTTP response code `403 - Forbidden` is returned from an API call the user does not have access to this resource.

### Logout

A session may be ended by sending a `GET` request to `/api/logout`

### Current User

The logged in user may be retrieved by sending a `GET` request to `/api/session/user`. The following JSON respon will be returned:

```json
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

### CSRF Protection

All requests made (excluding logout) require the `XSRF-TOKEN` cookie. This cookie is returned from the API Gateway when the first `GET` request is made.

### In Memory Users (for testing)

| Username | Password |
| -------- | -------- |
| bob      | password |
| sue      | password |

## APIs

| Base Path    | Description                                              |
| ------------ | -------------------------------------------------------- |
| /api/dept/** | Departments API, see https://github.com/zmad5306/gl-dept |
| /api/list/** | List API, see https://github.com/zmad5306/gl-list        |
| /api/item/** | Item API, see https://github.com/zmad5306/gl-item        |

### Postman Libraries

Postman libraries are avaialble for each environment. See the following files at the root of the project:

* GroceryList Development.postman_collection.json
* GroceryList Staging.postman_collection.json

## Continious Integration Builds

### Staging

**Name:** API Manger Staging CI Build

**Trigger type:** Branch

**Branch (regex):** ^develop$

**Build configration:** cloudbuild.yaml

**cloudbuild.yaml location:** /staging/cloudbuild.yaml

#### Staging Substitution variables

| Variable                    | Value               |
| --------------------------- | -----               |
| _CLOUDSDK_COMPUTE_ZONE      | us-central1-f       |
| _CLOUDSDK_CONTAINER_CLUSTER | staging             |
| _KMS_KEY                    | gl                  |
| _KMS_KEYRING                | gl-keyring          |
| _PROJECT_ID                 | grocery-list-205220 |
| _GIT_HUB_KEY_BUCKET_NAME    | gl-git-hub-key      |

### QA

**Name:** API Manger QA CI Build

**Trigger type:** Tag

**Tag (regex):** .*

**Build configration:** cloudbuild.yaml

**cloudbuild.yaml location:** /qa/cloudbuild.yaml

#### QA Substitution variables

| Variable                    | Value               |
| --------------------------- | -----               |
| _CLOUDSDK_COMPUTE_ZONE      | us-central1-f       |
| _CLOUDSDK_CONTAINER_CLUSTER | qa                  |
| _KMS_KEY                    | gl                  |
| _KMS_KEYRING                | gl-keyring          |
| _PROJECT_ID                 | grocery-list-205220 |
| _GIT_HUB_KEY_BUCKET_NAME    | gl-git-hub-key      |