FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/*.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
