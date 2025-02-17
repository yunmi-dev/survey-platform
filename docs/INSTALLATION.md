# 설치 가이드

## 목차
1. [개발 환경 설정](#1-개발-환경-설정)
2. [로컬 개발 환경 구성](#2-로컬-개발-환경-구성)
3. [프로덕션 환경 구성](#3-프로덕션-환경-구성)
4. [모니터링 환경 구성](#4-모니터링-환경-구성)
5. [문제 해결](#5-문제-해결)

## 1. 개발 환경 설정

### 1.1 필수 도구 설치

#### JDK 17 설치
```bash
# Windows (chocolatey)
choco install temurin17

# MacOS (homebrew)
brew install --cask temurin17

# Ubuntu
sudo apt-get update
sudo apt-get install temurin-17-jdk
```

#### Docker Desktop 설치
1. [Docker Desktop](https://www.docker.com/products/docker-desktop) 다운로드 후 실행
2. Windows의 경우 WSL 2 설치 필요

#### IDE 설치
1. [IntelliJ IDEA](https://www.jetbrains.com/idea/) 다운로드
2. 필수 플러그인 설치
   - Lombok
   - Spring Boot Assistant
   - SonarLint
   - MapStruct Support

### 1.2 프로젝트 클론

```bash
# 프로젝트 클론
git clone https://github.com/username/survey-platform.git
cd survey-platform

# gradle wrapper 권한 설정 (Linux/MacOS)
chmod +x ./gradlew
```

## 2. 로컬 개발 환경 구성

### 2.1 환경변수 설정

`.env.example` 파일을 `.env`로 복사

```bash
cp .env.example .env
```

`.env` 파일 설정

```properties
# Application
SERVER_PORT=8080
PROFILE=local
LOG_LEVEL=DEBUG

# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=survey
DB_USERNAME=root
DB_PASSWORD=password

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

# JWT
JWT_SECRET=your-jwt-secret-key
JWT_EXPIRATION=3600000

# AWS (로컬 테스트용)
AWS_ACCESS_KEY=your-access-key
AWS_SECRET_KEY=your-secret-key
AWS_REGION=ap-northeast-2
AWS_S3_BUCKET=your-bucket-name
```

### 2.2 데이터베이스 설정

#### Docker Compose로 실행
```yaml
# docker-compose.yml
version: '3.8'

services:
   mysql:
      image: mysql:8.0
      ports:
         - "3306:3306"
      environment:
         MYSQL_ROOT_PASSWORD: password
         MYSQL_DATABASE: survey
      volumes:
         - mysql-data:/var/lib/mysql
      command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

   redis:
      image: redis:7.0
      ports:
         - "6379:6379"
      volumes:
         - redis-data:/data

volumes:
   mysql-data:
   redis-data:
```

```bash
# 실행
docker-compose up -d
```

### 2.3 애플리케이션 실행

#### Gradle로 실행
```bash
# 테스트 실행
./gradlew test

# 애플리케이션 실행
./gradlew bootRun --args='--spring.profiles.active=local'
```

## 3. 프로덕션 환경 구성

### 3.1 인프라 구성

#### AWS 리소스 생성
1. VPC 및 서브넷 구성
2. EC2 인스턴스 생성
3. RDS (MySQL) 설정
4. ElastiCache (Redis) 설정
5. S3 버킷 생성

#### 도커 이미지 빌드
```bash
# 도커 이미지 빌드
docker build -t survey-platform .

# 도커 이미지 푸시
docker push your-registry/survey-platform:latest
```

### 3.2 Kubernetes 배포
#### kubectl 설정

```bash
# kubeconfig 설정
aws eks update-kubeconfig --name your-cluster-name
```

#### 매니페스트 파일
```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: survey-platform
spec:
   replicas: 3
   selector:
      matchLabels:
         app: survey-platform
   template:
      metadata:
         labels:
            app: survey-platform
      spec:
         containers:
            - name: survey-platform
              image: your-registry/survey-platform:latest
              ports:
                 - containerPort: 8080
              env:
                 - name: SPRING_PROFILES_ACTIVE
                   value: "prod"
              resources:
                 requests:
                    memory: "512Mi"
                    cpu: "500m"
                 limits:
                    memory: "1Gi"
                    cpu: "1000m"
```

```bash
# 배포
kubectl apply -f kubernetes/
```

## 4. 모니터링 환경 구성
### 4.1 Prometheus & Grafana 설정
#### Prometheus 설치
```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install prometheus prometheus-community/kube-prometheus-stack
```

#### Grafana 대시보드 구성
1. JVM 메트릭 대시보드
2. API 성능 대시보드
3. 비즈니스 메트릭 대시보드

### 4.2 로그 모니터링 (ELK Stack)
#### Elasticsearch & Kibana 설치
```bash
helm repo add elastic https://helm.elastic.co
helm install elasticsearch elastic/elasticsearch
helm install kibana elastic/kibana
```

## 5. 문제 해결
### 5.1 MySQL 접속 문제
```bash
# MySQL 컨테이너 로그 확인
docker logs mysql

# MySQL 접속 테스트
mysql -h localhost -u root -p
```

### 5.2 MySQL 연결 문제
```bash
# Redis CLI 접속
docker exec -it redis redis-cli

# Redis 연결 확인
docker exec -it redis-container redis-cli ping
```

### 5.3 빌드 문제
```bash
# Gradle 캐시 삭제 후 재시도
./gradlew clean build --refresh-dependencies
```

### 5.4 애플리케이션 문제
```bash
# 애플리케이션 로그 확인
tail -f logs/application.log

# 힙 덤프 생성
jmap -dump:format=b,file=heap.bin <pid>
```

### 5.5 성능 문제
```bash
# Thread dump 생성
jstack -l <pid> > thread-dump.txt

# GC 로그 분석
jstat -gcutil <pid> 1000
```