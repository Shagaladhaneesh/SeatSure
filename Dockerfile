
# Base Image
FROM eclipse-temurin:21-jdk

# Working Directory inside the container
WORKDIR /app

# Copy the JAR into the container
COPY target/*.jar app.jar

# Command to start the application
ENTRYPOINT ["java", "-jar", "app.jar"]