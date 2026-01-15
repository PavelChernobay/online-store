FROM openjdk:17-ea-3-jdk-slim

WORKDIR /app

COPY target/inventory-service.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
