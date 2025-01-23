#!/bin/bash

# 애플리케이션 경로 설정
APP_DIR="/home/ubuntu/app"
JAR_FILE="$APP_DIR/withDog-0.0.1-SNAPSHOT.jar"

# Parameter Store에서 환경변수 가져오기
echo "Fetching environment variables from Parameter Store..."
export DB_URL=$(aws ssm get-parameter --name "/config/env/DB_URL" --with-decryption --query "Parameter.Value" --output text)
export DB_USERNAME=$(aws ssm get-parameter --name "/config/env/DB_USERNAME" --with-decryption --query "Parameter.Value" --output text)
export DB_PASSWORD=$(aws ssm get-parameter --name "/config/env/DB_PASSWORD" --with-decryption --query "Parameter.Value" --output text)
export JWT_SECRET=$(aws ssm get-parameter --name "/config/env/JWT_SECRET" --with-decryption --query "Parameter.Value" --output text)
export GOOGLE_CLIENT_ID=$(aws ssm get-parameter --name "/config/env/GOOGLE_CLIENT_ID" --with-decryption --query "Parameter.Value" --output text)
export GOOGLE_CLIENT_SECRET=$(aws ssm get-parameter --name "/config/env/GOOGLE_CLIENT_SECRET" --with-decryption --query "Parameter.Value" --output text)
export KAKAO_CLIENT_ID=$(aws ssm get-parameter --name "/config/env/KAKAO_CLIENT_ID" --with-decryption --query "Parameter.Value" --output text)
export KAKAO_CLIENT_SECRET=$(aws ssm get-parameter --name "/config/env/KAKAO_CLIENT_SECRET" --with-decryption --query "Parameter.Value" --output text)

# 애플리케이션 시작
echo "Starting the application..."
nohup java -jar $JAR_FILE > $APP_DIR/app.log 2>&1 &

echo "Application started successfully."