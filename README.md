# IziCap Api

## Overview
IziCap API provides endpoints to retrieve company information based using SIRET numbers.

## Project Details
1. **Build RESTFul APIs** using `@RestController` and use of short annotation `@GetMapping`
2. **Service Layer** development using `@Service` to provide business logic to `@RestController`
3. **Global API Exception Handler** using `GlobalExceptionHandler` to customize the API response for errors
4. **Use of Async tasks**  using annotations `@EnableAsync` and `@Async` to make parallel calls using RestTemplate 
5. **API Documentation** using Spring OpenAPI and Swagger configuration using `ApiConfig`
6. **Boilerplate Code** generation using `Lombok` annotations such as `@AllArgsConstructor` `@ToString`, `@Getters`, and `@Setters`
7. **Unit and Integration Tests** for Controller

## How to use the API:
1. Clone repository.
2. In the application.properties file change property **local.storage.dir** to an appropriate path in docker container.
3. Change server.port if needed
4. If Docker installed, inside the project directory run: **docker build .**
5. Run docker run -p <your-port-external>:<docker-internal-port-exposed-for-access> IMAGE_ID
6. To request the API, use the Swagger documentation http://192.168.99.100:<your-port-external>/izicap-api/swagger-ui/index.html
  
