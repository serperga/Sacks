FROM openjdk:8-jdk-alpine
COPY target/codeexercise-1.0.0.jar codeexercise-1.0.0.jar
ENTRYPOINT ["java","-jar","/codeexercise-1.0.0.jar"]