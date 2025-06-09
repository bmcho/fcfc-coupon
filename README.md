# 🎫 FCFS-Coupon 프로젝트

**FCFS-Coupon**은 선착순(First-Come, First-Served) 방식으로 쿠폰을 발급하는 이벤트 시스템 예제입니다.  
대규모 트래픽 상황에서 **공정하고 안정적으로 쿠폰을 분배**하는 데 초점을 맞춘 구조로 설계되었습니다.

---

## 🔧 주요 특징

- ✅ Java & Spring 기반 마이크로서비스 구조
- ✅ API 서버, 이벤트 처리, 비즈니스 로직 분리
- ✅ Docker Compose로 손쉬운 로컬 환경 구축
- ✅ 로드테스트 및 모니터링 환경 내장
- ✅ 확장성과 유지보수성을 고려한 설계

---

## 📁 프로젝트 구조

```
fcfs-coupon/
├── coupon-api/ # 쿠폰 발급/관리용 API 서버
├── coupon-consumer/ # 쿠폰 발급 이벤트 처리(비동기 소비)
├── coupon-core/ # 쿠폰 도메인 및 핵심 비즈니스 로직
├── load-test/ # 부하 테스트 스크립트 및 설정
├── monitoring/ # 시스템 모니터링 도구 및 설정
├── gradle/ # Gradle 빌드 시스템 관련 파일
├── docker-compose.yml # 전체 서비스 오케스트레이션
├── build.gradle.kts # 프로젝트 빌드 설정 (Kotlin DSL)
├── settings.gradle.kts # Gradle 프로젝트 설정
├── gradlew* # Gradle Wrapper 실행 파일
└── .gitignore, .gitattributes
```


---

## 🛠️ 컴포넌트별 설명

### `coupon-api`
- 쿠폰 발급 및 관리용 REST API 서버
- 외부 트래픽을 받아 쿠폰 발급 요청을 처리

### `coupon-consumer`
- 발급 이벤트를 비동기적으로 처리
- 메시지 큐 기반의 이벤트 소비 및 후처리 담당

### `coupon-core`
- 쿠폰 도메인 모델 및 비즈니스 로직 구현
- 서비스 간 공통 로직 및 핵심 기능 제공

### `load-test`
- 시스템의 동시성, 성능 테스트 스크립트 및 환경

### `monitoring`
- 시스템 상태 및 성능 모니터링 도구/설정

### `docker-compose.yml`
- 여러 서비스를 한 번에 띄우는 오케스트레이션 설정

---

## 🛠️ 기술 스택

| 범주        | 기술                          |
|-------------|-------------------------------|
| Backend     | Java, Spring Boot, Gradle     |
| Database(cache)    | Mysql, redis, caffeine
| Infra       | Docker, Docker Compose        |
| Test/Load   | locust |
| Monitoring  | Prometheus, Grafana  |

---

⚠️ 참고 사항

⚠️ 이 프로젝트는 학습 및 구조 설계 목적의 예제입니다.
실 서비스 적용 시에는 인증, 보안, 장애 복구, 데이터 일관성 등을 추가로 고려해야 합니다.

