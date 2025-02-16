# API 명세서

## 개요
설문조사 플랫폼의 RESTful API 명세서입니다. 대규모 트래픽과 데이터를 고려한 확장 가능한 API 설계를 지향합니다.

## 공통 사항
### Base URL
- Local: `http://localhost:8080`
- Production: `https://api.survey-platform.com`

### API 버전 관리
- URL 기반 버저닝 사용 (/api/v1/*)
- 하위 호환성 보장을 위한 버전 관리

### 요청 헤더
```http
Content-Type: application/json
Authorization: Bearer {access_token}  // 인증이 필요한 API의 경우
X-API-VERSION: 1.0  // API 버전 명시
```

### 응답 형식

#### 성공 응답
- success: 요청 성공 여부 (true)
- data: 실제 응답 데이터가 포함되는 객체
- timestamp: 응답 생성 시각 (ISO-8601 형식)
- traceId: 요청 추적을 위한 고유 식별자

```json
{
    "success": true,
    "data": {
    },
    "timestamp": "2024-02-14T12:00:00",
    "traceId": "ab12-cd34-ef56"
}
```

#### 에러 응답
- success: 요청 실패 여부 (false)
- error: 에러 정보를 포함하는 객체
    - code: 에러 코드
    - message: 에러 메시지
    - timestamp: 에러 발생 시각 (ISO-8601 형식)
    - traceId: 요청 추적을 위한 고유 식별자

```json
{
    "success": false,
    "error": {
        "code": "ERROR_CODE",
        "message": "에러 메시지",
        "timestamp": "2024-02-14T12:00:00",
        "traceId": "ab12-cd34-ef56"
    }
}
```

## 성능 최적화
### Rate Limiting
- IP 기반: 초당 100회
- 사용자 기반: 초당 20회
- Redis를 활용한 rate limiting 구현

### 캐싱 전략
- 설문 목록: 5분 캐시
- 설문 상세: 1분 캐시
- 통계 데이터: 10분 캐시
- Redis Cache-Aside 패턴 적용

### Circuit Breaker
- 임계값: 50% 실패율
- 타임아웃: 3초
- 복구 시간: 30초
- Resilience4j 사용

## API 목록

### 1. 인증 API
#### 회원가입
```http
POST /api/v1/auth/signup
```
**Request Body**
```json
{
    "email": "user@example.com",
    "password": "password123",
    "name": "홍길동"
}
```
#### 응답 코드
- 201: 회원가입 성공
- 400: 잘못된 요청
- 409: 이메일 중복

#### 로그인
```http
POST /api/v1/auth/login
```
**Request Body**
```json
{
    "email": "user@example.com",
    "password": "password123"
}
```
#### 응답
```json
{
    "success": true,
    "data": {
        "accessToken": "eyJhbG...",
        "refreshToken": "eyJhbG...",
        "expiresIn": 3600
    }
}
```

### 2. 설문 API
#### 설문 생성
```http
POST /api/v1/surveys
Authorization: Bearer {access_token}
```
**Request Body**
```json
{
    "title": "고객 만족도 조사",
    "description": "서비스 개선을 위한 설문조사입니다.",
    "startDate": "2024-02-14",
    "endDate": "2024-02-28",
    "questions": [
        {
            "content": "서비스에 만족하시나요?",
            "type": "CHOICE",
            "required": true,
            "choices": [
                {"content": "매우 만족"},
                {"content": "만족"},
                {"content": "보통"}
            ]
        }
    ]
}
```
#### 성능 고려사항
- 대용량 설문의 경우 비동기 처리
- 첨부 파일은 S3 직접 업로드 후 서명된 URL 사용

#### 설문 목록 조회
```http
GET /api/v1/surveys?page=0&size=10&sort=createdAt,desc
```

#### 성능 최적화
- 페이지네이션: Cursor 기반 구현
- N+1 문제 해결을 위한 Fetch Join
- 캐시 적용 (Redis)

#### 설문 상세 조회
```http
GET /api/v1/surveys/{surveyId}
```

#### 성능 최적화
- 연관 데이터 Fetch Join
- Redis 캐싱
- ETag를 통한 캐시 검증

### 3. 응답 API
#### 설문 응답 제출
```http
POST /api/v1/surveys/{surveyId}/responses
Authorization: Bearer {access_token}
```

#### 동시성 제어
- 분산 락을 통한 동시성 제어
- 낙관적 락 적용

## 모니터링 및 로깅
### 성능 메트릭
- 응답 시간 (P95, P99)
- 초당 요청 수
- 에러율
- 캐시 히트율

### 로그
- 구조화된 JSON 로깅
- ELK 스택 연동
- 분산 추적 (Sleuth + Zipkin)

## 에러 코드

| 코드        | HTTP 상태 | 설명    |
|-----------|---------|-------|
| AUTH001   | 401     | 인증 실패 |
| AUTH002   | 401     | 토큰 만료 |
| AUTH003   | 403     | 권한 없음 |
| SURVEY001 | 404     | 설문 찾을 수 없음 |
| SURVEY002 | 400     | 설문 기간 만료 |
| SURVEY003 | 409     | 중복 응답 |

## API 테스트
- Swagger UI: http://localhost:8080/swagger-ui.html
- Postman Collection 제공
- 성능 테스트 스크립트 (JMeter)

## 보안
- JWT 기반 인증
- API Key 레이트 리밋
- XSS, CSRF 방어
- SQL Injection 방지