FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
