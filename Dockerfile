FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY . .
RUN ./mvnw clean install -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/coupon-0.0.1-SNAPSHOT.jar coupon-app.jar

EXPOSE 8080

CMD ["java", "-jar", "coupon-app.jar"]
