FROM openjdk:17-jdk-slim

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN apt-get update
# You can install some packages
#RUN apt-get install -y curl
# run under a user. This makes the whole thing more secure
RUN groupadd normalgroup
RUN useradd -G normalgroup normaluser
USER normaluser:normalgroup
# run app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]