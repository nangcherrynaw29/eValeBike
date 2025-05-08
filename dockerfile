FROM gradle:8.2.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

#runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/eValeBike-0.0.1.jar /app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
