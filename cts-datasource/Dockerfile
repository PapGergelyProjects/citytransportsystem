FROM openjdk:17-alpine
COPY /target/cts-datasource.jar /usr/bin/cts-datasource.jar
RUN mkdir -p /tmp/cts
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/bin/cts-datasource.jar"]