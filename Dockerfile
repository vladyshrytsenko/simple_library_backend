
FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/SimpleLibraryBackend-1.0-SNAPSHOT.jar /app/simple-library-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/simple-library-backend.jar"]
