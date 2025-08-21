FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app

COPY gradle gradle
COPY gradlew .
#COPY gradle.properties .
COPY settings.gradle .
COPY build.gradle .

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon -x test

FROM openjdk:17-jdk-alpine

RUN apk add --no-cache tzdata curl && \
    addgroup -g 1001 -S appuser && \
    adduser -S appuser -u 1001 -G appuser

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

RUN chown -R appuser:appuser /app

EXPOSE 8080
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]