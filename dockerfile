FROM gradle:8.4.0-jdk21 AS builder
USER root
WORKDIR /app
COPY . .
RUN rm -rf /home/gradle/.gradle/caches \
     && gradle clean bootJar --no-daemon --refresh-dependencies --stacktrace

#runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
# ENV SPRING_PROFILES_ACTIVE=pro