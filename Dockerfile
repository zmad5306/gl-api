FROM openjdk:10-jre-slim
ARG version
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/gl-api-${version}.jar /app/gl-api.jar
ENTRYPOINT ["java", "-jar", "/app/gl-api.jar"]