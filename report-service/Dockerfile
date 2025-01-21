FROM maven:3.9.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean install

FROM eclipse-temurin:17-alpine

COPY --from=build /app/target/report-service-0.0.1-SNAPSHOT.jar report-service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/report-service-0.0.1-SNAPSHOT.jar" ]