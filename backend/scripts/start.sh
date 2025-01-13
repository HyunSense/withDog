#!/bin/bash

# 애플리케이션 경로 설정
APP_DIR="/home/ubuntu/app"
JAR_FILE="$APP_DIR/withDog-0.0.1-SNAPSHOT.jar"

# 애플리케이션 시작
echo "Starting the application..."
nohup java -jar $JAR_FILE > $APP_DIR/app.log 2>&1 &

echo "Application started successfully."