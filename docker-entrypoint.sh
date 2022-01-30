#!/bin/bash

stop_container() {
  echo "trapped a signal, stopping the container"
  for pid in `ps -ef | egrep "python|java" | awk '{print $2}'`; do
    echo "killing pid $pid"
    kill -9 ${pid}
  done
  exit 0
}

echo "registering signals"
trap "stop_container" HUP INT QUIT TERM

echo "start place2be-server"

exec java -jar ${ARTIFACT}
