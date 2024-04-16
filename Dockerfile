FROM maven:3.8.4-openjdk-17-slim AS build
COPY target/gs-maven-0.1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
