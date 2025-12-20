# 1️⃣ Use official Java 21 image
FROM eclipse-temurin:21-jre

# 2️⃣ Set working directory inside container
WORKDIR /app

# 3️⃣ Copy the jar file into container
COPY target/portal-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ Expose port (same as Spring Boot)
EXPOSE 8080

# 5️⃣ Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
