FROM openjdk:21-jdk-slim

COPY ./build/libs/OrderService-0.1.jar OrderService-0.1.jar

VOLUME /tmp

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "OrderService-0.1.jar"]