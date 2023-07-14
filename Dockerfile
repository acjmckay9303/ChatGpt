# Use an appropriate base image for your Spring application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file and any other necessary files to the container
COPY target/gpt-chat.jar /app/gpt-chat.jar

# Expose the port on which your Spring application listens
EXPOSE 8080

# Set any necessary environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Define the command to run your Spring application
CMD ["java", "-jar", "gpt-chat.jar"]
