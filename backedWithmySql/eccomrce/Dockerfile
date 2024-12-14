# # Stage 1: Build the JAR file
# FROM maven:3.8.5-openjdk-17 AS build
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests

# # Stage 2: Run the JAR file
# FROM openjdk:17-jdk-slim
# WORKDIR /app
# COPY --from=build /app/target/eccomrce-0.0.1-SNAPSHOT.jar app.jar

# # Expose the application's port
# EXPOSE 8080

# # Run the Spring Boot application
# ENTRYPOINT ["java", "-jar", "app.jar"]


# Stage 1: Build the JAR file
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR file
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/eccomrce-0.0.1-SNAPSHOT.jar app.jar

# Add non-root user for security
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup
USER appuser

# Expose the application's port
EXPOSE 8080

# Health check (optional)
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
    CMD curl --fail http://localhost:8080/actuator/health || exit 1

# Set Java options and run the Spring Boot application
ENV JAVA_OPTS="-Xmx256m -Xms256m"
ENTRYPOINT ["java", "-jar", "app.jar"]
