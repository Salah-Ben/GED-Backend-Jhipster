# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the contents of the src/main/docker directory into the container at /app
COPY src/main/docker /app

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define environment variables
ENV _JAVA_OPTIONS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="dev,api-docs"
ENV MANAGEMENT_METRICS_EXPORT_PROMETHEUS_ENABLED="true"
ENV SPRING_DATASOURCE_URL="jdbc:mysql://mysql-ff62e13-benarbia-f7b5.a.aivencloud.com:27703/defaultdb?sslMode=REQUIRED&user=avnadmin&password=AVNS_TN3z3VQwSsAYqL1my_o"
ENV SPRING_LIQUIBASE_URL="jdbc:mysql://mysql-ff62e13-benarbia-f7b5.a.aivencloud.com:27703/defaultdb?sslMode=REQUIRED&user=avnadmin&password=AVNS_TN3z3VQwSsAYqL1my_o"
ENV JHIPSTER_SLEEP="30"

# Run the application
CMD ["java", "-jar", "icbged.jar"]
