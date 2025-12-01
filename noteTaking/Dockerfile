# Stage 1: Build the Spring Boot Application
FROM eclipse-temurin:21-jdk-jammy AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle gradle

# Copy and download dependencies (Gradle cache layer)
COPY build.gradle.kts settings.gradle.kts ./
RUN ./gradlew dependencies --offline || true

# Copy the entire source code
COPY src src

# Build the application
RUN ./gradlew clean bootJar

# Stage 2: Create the final, smaller runtime image
FROM eclipse-temurin:21-jre-jammy

# Set the working directory
WORKDIR /app

# Expose the application port (default for Spring Boot)
EXPOSE 8080

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]