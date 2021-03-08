#!/usr/bin/env bash

java -cp ~/.m2/repository/com/h2database/h2/1.4.200/h2-1.4.200.jar \
     org.h2.tools.Server \
     -web \
     -webAllowOthers \
     -tcp \
     -tcpAllowOthers \
     -baseDir ./data \
     -ifNotExists &

sleep 1

echo H2 Database Ready

echo JDBC_URL: jdbc:h2:tcp://localhost/./todos
echo Username: sa
echo Password: sa