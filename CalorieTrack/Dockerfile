FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk AS runner
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Запуск приложения
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
