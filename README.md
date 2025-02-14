# survey-platform

> Spring Boot와 AWS를 활용한 설문조사 플랫폼

[![Java](https://img.shields.io/badge/Java-17-007396?logo=java)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.2-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![JPA](https://img.shields.io/badge/JPA-3.2.x-6DB33F?logo=hibernate)](https://spring.io/projects/spring-data-jpa)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0-DC382D?logo=redis&logoColor=white)](https://redis.io/)
[![Docker](https://img.shields.io/badge/Docker-24.0.5-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![AWS](https://img.shields.io/badge/AWS-EC2_&_RDS-232F3E?logo=amazonaws)](https://aws.amazon.com/)


## 목차
- [소개](#프로젝트-소개)
- [기술 스택](#기술-스택)
- [시작하기](#실행-방법)
- [프로젝트 구조](#프로젝트-구조)
- [문서](#문서)
- [프로젝트 목표](#프로젝트-목표)
- [라이센스](#라이센스)


## 프로젝트 소개

설문조사 플랫폼은 대규모 설문 데이터를 효율적으로 처리하고 분석할 수 있는 백엔드 시스템입니다. 객체지향 설계 원칙을 준수하고 성능 최적화를 통해 안정적인 서비스 운영이 가능하도록 구현합니다.


### 핵심 기능

- 다양한 유형의 설문 문항 생성 및 관리
- Spring Security + JWT 기반 사용자 인증
- Redis 캐싱을 통한 성능 최적화
- 설문 결과 실시간 통계 분석

자세한 내용은 기능 명세서를 참고해주세요.


### 기술 스택

#### Backend
- **Language & Framework**: Java 17, Spring Boot 3.4.x
- **ORM**: Spring Data JPA, QueryDSL
- **Security**: Spring Security, JWT
- **DB**: MySQL 8.0, Redis (캐싱)
- **Test**: JUnit 5, Mockito
- **Documentation**: Swagger/Spring Rest Docs

#### DevOps
- **Cloud**: AWS (EC2, RDS)
- **Container**: Docker, Docker Compose
- **CI/CD**: GitHub Actions


### 시작하기

#### 요구사항

- JDK 17
<<<<<<< HEAD

- Docker Desktop

=======
  
- Docker Desktop
  
>>>>>>> 02ef0908b7b3f592ac98eaa869ad94ef50c64a96
- MySQL 8.0

#### 실행 방법
```bash
# 프로젝트 클론
git clone https://github.com/yunmi-dev/survey-platform.git

# 환경변수 설정
cp .env.example .env

# 도커 컨테이너 실행
docker-compose up -d

# 애플리케이션 실행
./gradlew bootRun
```
자세한 설정 방법은 설치 가이드를 참고해주세요.


### 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com/survey/
│   │       ├── domain/      # 도메인 계층
│   │       ├── global/      # 공통 설정
│   │       └── infra/       # 인프라 계층
│   └── resources/
└── test/
```
전체 구조는 프로젝트 구조 문서를 참고해주세요.


### 문서
- [API 명세서](./docs/API_SPECIFICATION.md)
- [ERD 설계](./docs/ERD.md)
- [설치 가이드](./docs/INSTALLATION.md)
- [기여 가이드](./docs/CONTRIBUTING.md)


### 프로젝트 진행 현황

### 1차 목표 (2-3주)
- [ ] 기본적인 CRUD 기능 구현
- [ ] Spring Security + JWT 인증 구현
- [ ] JPA 연관관계 매핑
- [ ] 단위 테스트 작성

### 2차 목표 (2-3주)
- [ ] Redis 캐싱 적용
- [ ] AWS 배포
- [ ] CI/CD 구축

## 라이센스
MIT © Yunmi Jeong