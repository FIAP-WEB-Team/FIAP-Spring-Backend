FROM openjdk:18-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY build/libs/spring-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/config/service_key.json service_key.json
ENTRYPOINT ["java","-jar","/app.jar"]