FROM openjdk:8
ADD target/test_task-RELEASE.jar test_task-RELEASE.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "test_task-RELEASE.jar"]