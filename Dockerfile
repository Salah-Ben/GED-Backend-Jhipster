FROM maven:3.9.4-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package
FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="your-email@example.com"
LABEL app="demo-app"
WORKDIR /app

# Copy the JAR file from the previous stage (build stage)
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar demo.jar
# Run the application
EXPOSE 8089
CMD ["java", "-jar", "demo.jar"]
