FROM adoptopenjdk/openjdk11:alpine-jre

ARG APP_NAME="izicap"
ARG APP_VERSION="0.0.1"
ARG JAR_FILE="target/${APP_NAME}-${APP_VERSION}.jar"

COPY ${JAR_FILE} izicap-api.jar
ENTRYPOINT [ "java","-jar","izicap-api.jar"]