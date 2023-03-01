FROM openjdk:17
EXPOSE 8787
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cinemas.jar
RUN mvn clean install
ENTRYPOINT ["java","-jar","/cinemas.jar"]