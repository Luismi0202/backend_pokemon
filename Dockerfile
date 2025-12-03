# Dockerfile
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app
COPY . .

# Compilar el JAR
RUN ./gradlew shadowJar

# Imagen final ligera
FROM openjdk:21-slim
WORKDIR /app

# Copiar el JAR compilado
COPY --from=builder /app/build/libs/*-all.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
