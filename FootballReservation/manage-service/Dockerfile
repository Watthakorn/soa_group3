FROM frolvlad/alpine-oraclejdk8:slim 
ADD  ./target/manage-service.jar app.jar 
ENTRYPOINT [ "sh", "-c", "java -jar app.jar" ]