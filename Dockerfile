
FROM maven:3.8.5-openjdk-17 AS builder


WORKDIR /app


COPY pom.xml .


COPY src ./src


RUN mvn clean install -DskipTests


FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app


COPY --from=builder /app/target/*.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java","-jar","app.jar"]