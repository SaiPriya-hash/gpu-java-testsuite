# Use official Maven image with JDK 21
FROM maven:3.9.6-eclipse-temurin-21 as build

# Set working directory inside container
WORKDIR /app

# Copy source files and pom.xml
COPY . .

# Build the project (clean + test + package shaded jar)
RUN mvn clean package

# Stage 2: Minimal runtime container
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy built jar from the previous stage
COPY --from=build /app/target/gpu-factory-test-framework-1.0-SNAPSHOT-shaded.jar app.jar

# Default command to run the test suite
ENTRYPOINT ["java", "-jar", "app.jar"]
