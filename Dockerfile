FROM openjdk:17-jdk-slim

LABEL maintainer="rospielberg@gmail.com"

ENV LANG C.UTF-8

COPY target/*.jar /app/app.jar

CMD java -jar /app/app.jar $APP_OPTIONS
