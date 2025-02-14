# survey-platform

> Spring Boot와 AWS를 활용한 확장 가능한 설문조사 플랫폼

[![Java](https://img.shields.io/badge/Java-17-007396?logo=java)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.x-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![JPA](https://img.shields.io/badge/JPA-3.2.x-6DB33F?logo=hibernate)](https://spring.io/projects/spring-data-jpa)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0-DC382D?logo=redis&logoColor=white)](https://redis.io/)
[![Docker](https://img.shields.io/badge/Docker-24.0.5-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![AWS](https://img.shields.io/badge/AWS-EC2_&_RDS-232F3E?logo=amazonaws)](https://aws.amazon.com/)


## 프로젝트 소개

설문조사 플랫폼은 대규모 설문 데이터를 효율적으로 처리하고 분석할 수 있는 백엔드 시스템입니다. MSA로의 확장을 고려한 모듈형 설계를 적용하였으며, 캐싱 전략과 성능 최적화를 통해 안정적인 서비스 운영이 가능하도록 구현할 예정입니다.

### 핵심 기능

#### 1. 설문 관리
- 다양한 유형의 설문 문항 생성 및 관리
- 설문 템플릿 기능으로 재사용성 확보
- 설문 결과의 실시간 집계 및 분석

#### 2. 사용자 관리
- JWT 기반 인증/인가 시스템
- OAuth2.0 소셜 로그인 (Google, GitHub)
- RBAC(Role Based Access Control) 구현

#### 3. 데이터 처리
- Redis 캐싱으로 조회 성능 최적화
- 대용량 설문 응답 처리 로직 구현
- 집계 데이터 배치 처리

## 기술 스택

### Backend
- **Language & Framework**: Java 17, Spring Boot 3.2.x
- **ORM**: Spring Data JPA, QueryDSL
- **Security**: Spring Security, JWT
- **DB**: MySQL 8.0, Redis (캐싱)
- **Test**: JUnit 5, Mockito, TestContainers

### DevOps
- **Cloud**: AWS (EC2, RDS, S3)
- **Container**: Docker, Docker Compose
- **CI/CD**: GitHub Actions
- **Monitoring**: Prometheus, Grafana

## 실행 방법

### 요구사항
- JDK 17
- Docker & Docker Compose
- MySQL 8.0

### 실행 단계
```bash
# 프로젝트 클론
git clone https://github.com/[username]/survey-platform.git

# 환경변수 설정
cp .env.example .env

# 도커 컨테이너 실행
docker-compose up -d

# 애플리케이션 실행
./gradlew bootRun
```

## 프로젝트 목표

### 1차 목표

- Spring Boot & JPA 핵심 개념 실습
- 클린 아키텍처 기반의 계층형 설계 구현
- 데이터 정합성과 성능 최적화 경험

### 2차 목표

- 테스트 커버리지 80% 이상 달성
- Redis 캐싱 적용
- AWS 배포 및 CI/CD 구축

라이센스
MIT © Yunmi Jeong
