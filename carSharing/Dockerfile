FROM openjdk:21-jdk-slim

COPY ./build/libs/carSharing-0.1.jar carSharing-0.1.jar

VOLUME /tmp

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "carSharing-0.1.jar"]