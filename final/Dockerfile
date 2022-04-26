FROM openjdk:11-jre-slim
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080