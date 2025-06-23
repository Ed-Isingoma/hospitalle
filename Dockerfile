FROM tomcat:9.0-jdk11-openjdk-slim
LABEL stage=runtime

RUN rm -rf ./webapps/ROOT

COPY ./target/*.war ./webapps/container123.war

EXPOSE 8080

ENTRYPOINT ["catalina.sh", "run"]
