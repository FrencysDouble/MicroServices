FROM openjdk:21-jdk-slim

COPY ./build/libs/EurekaServer-0.0.1.jar EurekaServer-0.0.1.jar

VOLUME /tmp

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "EurekaServer-0.0.1.jar"]