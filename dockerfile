FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/server-0.0.1-SNAPSHOT.jar app.jar

COPY application.properties /app/application.properties

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=/app/application.properties"]
