FROM openjdk:8-jdk-alpine
COPY target/codeexercise-0.0.1-SNAPSHOT.jar codeexercise-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/codeexercise-0.0.1-SNAPSHOT.jar"]