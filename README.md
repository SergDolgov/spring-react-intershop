# Spring-react-intershop

The goal of this project is to implement an application called `intershop-app` to manage products. For it, we will implement a back-end [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) application called `intershop-api` and a font-end [React](https://react.dev/) application called `intershop-ui`. Besides, we will use [`OAuth2`](https://en.wikipedia.org/wiki/OAuth#OAuth_2.0) (Social Login) to secure both applications.

## Proof-of-Concepts & Articles

On [sergdolgov.github.io](https://sergdolgov.github.io), I have compiled my Proof-of-Concepts (PoCs) and articles. You can easily search for the technology you are interested in by using the filter. Who knows, perhaps I have already implemented a PoC or written an article about what you are looking for.

## Additional Readings

new commit here

## Project Diagram

project diagram

## Applications

- ### intershop-api

  `Spring Boot` Web Java backend application that exposes a Rest API to create, retrieve and delete products. If a user has `ADMIN` role he/she can also retrieve information of other users or delete them. The application secured endpoints can just be accessed if a valid JWT access token is provided.
  
  In order to get the JWT access token, the user can login using the credentials (`username` and `password`) created when he/she signed up directly to the application.
  
  `intershop-api` stores its data in [`Postgres`](https://www.postgresql.org/) database.

  `intershop-api` has the following endpoints

  | Endpoint                                                      | Secured | Roles           |
  | ------------------------------------------------------------- | ------- | --------------- |
  | `POST /auth/authenticate -d {"username","password"}`          | No      |                 |
  | `POST /auth/signup -d {"username","password","name","email"}` | No      |                 |
  | `GET /public/numberOfUsers`                                   | No      |                 |
  | `GET /public/numberOfProducts`                                | No      |                 |
  | `GET /api/users/me`                                           | Yes     | `ADMIN`, `USER` |
  | `GET /api/users`                                              | Yes     | `ADMIN`         |
  | `GET /api/users/{username}`                                   | Yes     | `ADMIN`         |
  | `DELETE /api/users/{username}`                                | Yes     | `ADMIN`         |
  | `GET /api/products [?text]`                                   | Yes     | `ADMIN`, `USER` |
  | `POST /api/products -d {"imdb","description"}`                | Yes     | `ADMIN`         |
  | `DELETE /api/products/{imdb}`                                 | Yes     | `ADMIN`         |

- ### intershop-ui

  `React` frontend application where a user with role `USER` can retrieve the information about products. On the other hand, a user with role `ADMIN` has access to all secured endpoints, including endpoints to create and delete products.
  
  In order to access the application, a `user` or `admin` can login using his/her `Github` account or using the credentials (`username` and `password`) created when he/she signed up directly to the application. All the requests coming from `intershop-ui` to secured endpoints in `intershop-api` have the JWT access token. This token is generated when the `user` or `admin` logins.
  
  `intershop-ui` uses [`Semantic UI React`](https://react.semantic-ui.com/) as CSS-styled framework.

## Creating OAuth2 apps for Social Login

- **Github**

  In the **Medium** article, 
- 
- **Google**

  In the **Medium** article, 
- 
## How Social Login Works?

In the **Medium** article, 

## Prerequisites

- [`npm`](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
- [`Java 17+`](https://www.oracle.com/java/technologies/downloads/#java17)
- [`Docker`](https://www.docker.com/)
- [`jq`](https://stedolan.github.io/jq)

## Start Environment

- In a terminal, make sure you are inside `spring-react-intershop` root folder

- Run the following command to start docker compose containers
  ```
  docker compose up -d
  ```

## Running intershop-app using Maven & Npm

- **intershop-api**

  - Open a terminal and navigate to `spring-react-intershop/intershop-api` folder

  - Export the following environment variables for the `Client ID` and `Client Secret` of the Social Apps (see how to get them in [Creating OAuth2 apps for Social Login](#creating-oauth2-apps-for-social-login))
    ```
    export GITHUB_CLIENT_ID=...
    export GITHUB_CLIENT_SECRET=...
    export GOOGLE_CLIENT_ID=...
    export GOOGLE_CLIENT_SECRET=...
    ```

  - Run the following `Maven` command to start the application
    ```
    ./mvnw clean spring-boot:run
    ```

- **intershop-ui**

  - Open another terminal and navigate to `spring-react-intershop/intershop-ui` folder

  - Run the command below if you are running the application for the first time
    ```
    npm install
    ```

  - Run the `npm` command below to start the application
    ```
    npm start
    ```

## Applications URLs

| Application  | URL                                   | Credentials                                         |
| ------------ | ------------------------------------- | --------------------------------------------------- |
| intershop-api    | http://localhost:8080/swagger-ui.html |                                                     |
| intershop-ui     | http://localhost:3000                 | `admin/admin`, `user/user` or signing up a new user |

## Demo

- The gif below shows a `user` loging in using the `Github`



- The gif below shows an `admin` loging in using his application account



## Testing intershop-api Endpoints

- **Manual Test**

  - Access `intershop-ui` at http://localhost:3000

  - Click `Login` and then, connect with `Github`
  
  - Provide your `Github` credentials

- **Automatic Endpoints Test**

  - Open a terminal and make sure you are in `spring-react-intershop` root folder

  - Run the following script
    ```
    ./intershop-api/test-endpoints.sh
    ```
    It should return something like the output below, where it shows the http code for different requests
    ```
    POST auth/authenticate
    ======================
    admin access token
    ------------------
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODY2MjM1MjksImlhdCI6MTU4Nj..._ha2pM4LSSG3_d4exgA
    
    user access token
    -----------------
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODY2MjM1MjksImlhdCIyOSwian...Y3z9uwhuW_nwaGX3cc5A
    
    POST auth/signup
    ================
    user2 access token
    ------------------
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODY2MjM1MjksImanRpIjoiYTMw...KvhQbsMGAlFov1Q480qg

    Authorization
    =============
                    Endpoints | without token |  user token |  admin token |
    ------------------------- + ------------- + ----------- + ------------ |
     GET public/numberOfUsers |           200 |         200 |          200 |
    GET public/numberOfProducts |           200 |         200 |          200 |
    ......................... + ............. + ........... + ............ |
            GET /api/users/me |           401 |         200 |          200 |
               GET /api/users |           401 |         403 |          200 |
         GET /api/users/user2 |           401 |         403 |          200 |
      DELETE /api/users/user2 |           401 |         403 |          200 |
    ......................... + ............. + ........... + ............ |
              GET /api/products |           401 |         200 |          200 |
             POST /api/products |           401 |         403 |          201 |
       DELETE /api/products/abc |           401 |         403 |          200 |
    ------------------------------------------------------------------------
     [200] Success -  [201] Created -  [401] Unauthorized -  [403] Forbidden
    ```

## Util Commands

- **Postgres**
  ```
  docker exec -it postgres psql -U postgres -d productdb
  \dt
  ```

## Shutdown

- To stop `intershop-api` and `intershop-ui`, go to the terminals where they are running and press `Ctrl+C`

- To stop and remove docker compose containers, network and volumes, go to a terminal and, inside `spring-react-intershop` root folder, run the command below
  ```
  docker compose down -v
  ```

## How to upgrade intershop-ui dependencies to latest version

- In a terminal, make sure you are in `spring-react-intershop/intershop-ui` folder

- Run the following commands
  ```
  npm upgrade
  npm i -g npm-check-updates
  ncu -u
  npm install
  ```

## References

- https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-1/
