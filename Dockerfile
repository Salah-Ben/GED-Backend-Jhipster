FROM openjdk:11-jre-slim

WORKDIR /app

COPY . /app

EXPOSE 8080

ENV _JAVA_OPTIONS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="dev,api-docs"
ENV MANAGEMENT_METRICS_EXPORT_PROMETHEUS_ENABLED="true"
ENV SPRING_DATASOURCE_URL="jdbc:mysql://mysql-ff62e13-benarbia-f7b5.a.aivencloud.com:27703/defaultdb?sslMode=REQUIRED&user=avnadmin&password=AVNS_TN3z3VQwSsAYqL1my_o"
ENV SPRING_LIQUIBASE_URL="jdbc:mysql://mysql-ff62e13-benarbia-f7b5.a.aivencloud.com:27703/defaultdb?sslMode=REQUIRED&user=avnadmin&password=AVNS_TN3z3VQwSsAYqL1my_o"
ENV JHIPSTER_SLEEP="30"

CMD ["java", "-jar", "icbged.jar"]
