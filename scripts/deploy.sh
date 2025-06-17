#!/bin/bash

APP_DIR="/home/ubuntu/app/withdog"
cd $APP_DIR

# Parameter Store에서 환경변수 가져오기
echo "Fetching environment variables from Parameter Store..."

export DB_URL=$(aws ssm get-parameter --name "/config/env/DB_URL" --with-decryption --query "Parameter.Value" --output text)
export DB_USERNAME=$(aws ssm get-parameter --name "/config/env/DB_USERNAME" --with-decryption --query "Parameter.Value" --output text)
export DB_PASSWORD=$(aws ssm get-parameter --name "/config/env/DB_PASSWORD" --with-decryption --query "Parameter.Value" --output text)
export AWS_ACCESS_KEY=$(aws ssm get-parameter --name "/config/env/AWS_ACCESS_KEY" --with-decryption --query "Parameter.Value" --output text)
export AWS_SECRET_KEY=$(aws ssm get-parameter --name "/config/env/AWS_SECRET_KEY" --with-decryption --query "Parameter.Value" --output text)
export JWT_SECRET=$(aws ssm get-parameter --name "/config/env/JWT_SECRET" --with-decryption --query "Parameter.Value" --output text)
export GOOGLE_CLIENT_ID=$(aws ssm get-parameter --name "/config/env/GOOGLE_CLIENT_ID" --with-decryption --query "Parameter.Value" --output text)
export GOOGLE_CLIENT_SECRET=$(aws ssm get-parameter --name "/config/env/GOOGLE_CLIENT_SECRET" --with-decryption --query "Parameter.Value" --output text)
export KAKAO_CLIENT_ID=$(aws ssm get-parameter --name "/config/env/KAKAO_CLIENT_ID" --with-decryption --query "Parameter.Value" --output text)
export KAKAO_CLIENT_SECRET=$(aws ssm get-parameter --name "/config/env/KAKAO_CLIENT_SECRET" --with-decryption --query "Parameter.Value" --output text)
export REDIS_HOST=$(aws ssm get-parameter --name "/config/env/REDIS_HOST" --with-decryption --query "Parameter.Value" --output text)
export REDIS_PORT=$(aws ssm get-parameter --name "/config/env/REDIS_PORT" --with-decryption --query "Parameter.Value" --output text)
export REDIS_PASSWORD=$(aws ssm get-parameter --name "/config/env/REDIS_PASSWORD" --with-decryption --query "Parameter.Value" --output text)