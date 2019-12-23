FROM openjdk:8
ADD . /opt/
COPY obf/target/obf-1.0.jar /opt/
EXPOSE 8080
ENTRYPOINT java -jar /opt/obf-1.0.jar
