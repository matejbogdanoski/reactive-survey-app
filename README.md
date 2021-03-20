# How to start the app

<details>
<summary>Docker</summary>

### Prerequisites
* docker
* docker-compose
### Run the app
* Run in terminal:
  ```
  bash ./start-with-docker.sh
  ```
</details>

<hr>

### Database
* Install postgresql 12
* Create super user named reactive_survey
     ```
     sudo su postgres
     psql
     create user reactive_survey with superuser password 'reactive_survey';
     ```
* Run reset_or_init_database.sh

    ```
    bash reset_or_init_database.sh
    ```
  
### Backend
* Java 11+
* Run with gradle

    ```
    ./gradlew bootRun
    ```
### Frontend
* node v14+
* npm 6+
* install packages in the `/ng-reactive-survey` directory
    ```
    npm i
    ```
* Run with npm in the `/ng-reactive-survey` directory

    ```
    npm start
    ```

