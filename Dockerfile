FROM openjdk:8-jdk-alpine
RUN addgroup -S apiusers && adduser -S izicapapi -G apiusers
USER apiusers:izicapapi
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} izicap-api.jar
ENTRYPOINT [ "java","-jar","/izicap-api.jar"]