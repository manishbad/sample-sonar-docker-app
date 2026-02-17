FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/sample-sonar-docker-app-1.0.jar app.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]

