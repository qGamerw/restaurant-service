FROM adoptopenjdk/openjdk17:alpine-jre
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app/restaurant-service
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","restaurant.jar"]