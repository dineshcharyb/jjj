FROM openjdk:11
ADD target/migrationservice-0.0.1-SNAPSHOT.jar migrationservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","migrationservice-0.0.1-SNAPSHOT.jar"]
