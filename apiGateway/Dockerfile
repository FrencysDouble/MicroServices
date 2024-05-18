FROM openjdk:21-jdk-slim

COPY ./build/libs/api-gateway-0.1.jar api-gateway-0.1.jar

VOLUME /tmp

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-gateway-0.1.jar"]