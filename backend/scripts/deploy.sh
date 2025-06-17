#!/bin/bash

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

# Docker Hub에서 이미지 pull
echo "Pulling latest image from Docker Hub..."
docker compose pull withdog-api

echo "Restarting services with docker compose..."
docker compose up -d --no-deps withdog-api

# 사용하지 않고 태그가 없는 이미지 삭제
docker image prune -f