# timetable-server
Spring Boot project with apis to generate timetables

## Getting started
To run this project locally:
1. Setup MongoDB in your local
2. Add a `application-local.properties` file under `src/main/resources` folder and configure the following:
```ruby
spring.data.mongodb.host=
spring.data.mongodb.port=
spring.data.mongodb.database=your db name
```
3. That's it! Your'e good to run your application 😀.

## Swagger Documentation

The swagger documentation for this project can be viewed in the following url if you are running on `localhost:8080`:
http://localhost:8080/api/time-table/swagger-ui/index.html

Change your server details accordingly.
