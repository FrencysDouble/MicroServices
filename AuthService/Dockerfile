# Используем официальный образ Java 21
FROM openjdk:21-jdk-slim

# Копируем собранный JAR-файл первого микросервиса в контейнер
COPY ./build/libs/AuthService-0.0.1.jar AuthService-0.0.1.jar

# Устанавливаем точку монтирования
VOLUME /tmp

# Указываем порт, на котором будет работать первый микросервис
EXPOSE 8080

# Запускаем первый микросервис при старте контейнера
ENTRYPOINT ["java", "-jar", "AuthService-0.0.1.jar"]