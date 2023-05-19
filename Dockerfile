FROM openjdk:11 as builder

COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/honey-mart.jar ./honey-mart.jar

CMD ["java", "-jar", "honey-mart.jar"]