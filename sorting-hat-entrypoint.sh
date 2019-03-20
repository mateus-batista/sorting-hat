#!/bin/sh

sleep 10

while ! nc -z sortinghat-db 5432 ; do
    echo "Waiting for upcoming database"
    sleep 2
done

java -jar /opt/sorting-hat/lib/sorting-hat-0.0.1-SNAPSHOT.jar