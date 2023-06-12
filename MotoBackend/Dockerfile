FROM maven:3.8.4-openjdk-17-slim AS build-stage
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim AS production-stage
COPY --from=build-stage /app/target/*.jar app.jar
COPY src/main/java/com/example/TestBackend/drivers.xml /app/drivers.xml
COPY src/main/java/com/example/TestBackend/drivers.json /app/drivers.json
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

