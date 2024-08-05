FROM 10.10.30.37/base/openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]

CMD java -jar /app.jar --spring.config.location=file:///config/application.properties -Duser.timezone="GMT+7"

ENV TZ="Asia/Jakarta"


EXPOSE 8080
