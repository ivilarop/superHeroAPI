#
# Build stage
#
FROM maven:3.8.4-openjdk-17 AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f app/pom.xml clean package
CMD mvn spring-boot:run

#
# Run stage
#
FROM openjdk:17
LABEL name="superheroes API" \
description="Docker image for API superheroes"
ARG JAR_FILE=target/superheroAPI-1.0.0.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]