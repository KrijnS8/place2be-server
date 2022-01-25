#!/bin/bash

stop_container() {
  echo "Trapped a signal! Stopping the container!"
  for pid in `ps -ef | egrep "python|java|mysql" | awk '{print $2}'`; do
    echo "Killing PID $pid"
    kill -9 ${pid}
  done
  exit 0
}

echo "registering signals..."
trap "stop_container" HUP INT QUIT TERM

echo "start place2be-server..."

exec java -jar ${ARTIFACT}
