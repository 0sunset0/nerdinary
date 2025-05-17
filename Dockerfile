FROM openjdk:17
COPY ./hackathon/build/libs/hackathon-0.0.1-SNAPSHOT.jar hackathon.jar
ENTRYPOINT ["java", "-jar", "hackathon.jar"]