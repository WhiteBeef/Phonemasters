FROM openjdk:23-ea-17-slim
WORKDIR /app
COPY /build/libs/Phonemasters-0.0.1-SNAPSHOT.jar phonemasters.jar
ENTRYPOINT ["java", "-jar", "phonemasters.jar"]