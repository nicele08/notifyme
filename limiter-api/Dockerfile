# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .mvn/

# Download Maven dependencies (this is done separately from the app build)
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

# Copy application source code
COPY src/ src/

# Build the application
RUN ./mvnw package -DskipTests

# Copy JAR file to working directory
RUN cp target/*.jar app.jar

# Set environment variables
ENV JAVA_HOME=/opt/java/openjdk \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

# Expose the port that the application listens on
EXPOSE 8080

# Set the entry point for the application
ENTRYPOINT ["java", "-jar", "app.jar"]
