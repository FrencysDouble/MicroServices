FROM openjdk:21-jdk-slim

COPY ./build/libs/AuthService-0.0.1.jar AuthService-0.0.1.jar

VOLUME /tmp

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "AuthService-0.0.1.jar"]