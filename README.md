# To run locally:
### 1. Edit backend/src/main/resources/application.properties
###### Comment out:
```
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```
###### Uncomment:
```
#spring.datasource.url=jdbc:postgresql://localhost:5555/forumdb
#spring.datasource.username=postgres
#spring.datasource.password=postgres
#server.port=8888
```

### 2. Create and run database server
``
./build-docker.sh
``
### 3. Build and run app
``
./build-app.sh
``
### 4. App running on http://localhost:8888/

