FROM openjdk:11
ADD target/ib-fx-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]