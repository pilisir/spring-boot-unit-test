FROM openjdk:21-jdk

WORKDIR /app

COPY target/my-web-sb-0.0.1-SNAPSHOT.war /app/my-web-sb-v1-demo.war

EXPOSE 8080

#CMD ["java", "-jar", "my-web-sb-v1-demo.jar"]