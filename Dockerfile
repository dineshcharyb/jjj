FROM openjdk:11
ADD target/Sample-CURD-Application-0.0.1-SNAPSHOT.jar Sample-CURD-Application-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Sample-CURD-Application-0.0.1-SNAPSHOT.jar"]
