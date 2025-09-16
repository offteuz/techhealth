# --- ESTÁGIO 1: O AMBIENTE DE BUILD (CONSTRUTOR) ---
FROM maven:3.9-eclipse-temurin-23 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: O AMBIENTE FINAL DE EXECUÇÃO ---
FROM openjdk:23-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/techhealth-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]