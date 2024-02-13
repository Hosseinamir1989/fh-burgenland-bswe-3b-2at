FROM gradle:jdk21-jammy as build
LABEL authors="hamir"


WORKDIR /app


COPY / /app

RUN ./gradlew bootJar

FROM gradle:jdk21-jammy as run

WORKDIR /app


COPY --from=build /app/build/libs/*.jar /app/examination.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "examination.jar"]