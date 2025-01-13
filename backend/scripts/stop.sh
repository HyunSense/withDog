#!/bin/bash

# 실행 중인 Java 프로세스 종료
echo "Stopping the application..."
pid=$(ps -ef | grep 'java -jar' | grep -v grep | awk '{print $2}')
if [ ! -z "$pid" ]; then
    kill -9 $pid
    echo "Application stopped successfully with PID $pid"
else
    echo "No running application found."
fi