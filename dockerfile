FROM gradle:8.2.1-jdk17 AS builder
USER root
WORKDIR /app
COPY . .
RUN rm -rf /home/gradle/.gradle/caches \
     && gradle clean bootJar --no-daemon --refresh-dependencies clean build

#runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/eValeBike-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "eValeBike-0.0.1-SNAPSHOT.jar"]
