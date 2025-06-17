# 빌드 환경 (builder)
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x ./gradlew
RUN ./gradlew dependencies

COPY src src

RUN ./gradlew -x test clean build

# 실행 환경
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar withdog-api.jar

CMD ["java", "-jar", "withdog-api.jar"]
