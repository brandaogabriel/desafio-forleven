FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/challenge-forleven-0.0.1-SNAPSHOT.jar forleven.jar
ENTRYPOINT ["java","-jar","/forleven.jar"]