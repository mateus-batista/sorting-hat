FROM openjdk:8-jdk-alpine
RUN apk --no-cache add netcat-openbsd
COPY sorting-hat-entrypoint.sh /opt/sorting-hat/bin/
COPY target/sorting-hat-0.0.1-SNAPSHOT.jar /opt/sorting-hat/lib/
RUN chmod 755 /opt/sorting-hat/bin/sorting-hat-entrypoint.sh