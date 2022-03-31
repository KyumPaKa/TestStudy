## 운영 이슈 테스트(Chaos Monkey)

### Chaos Monkey 소개
- 카오스 엔지니어링 툴 <br>
프로덕션 환경, 특히 분산 시스템 환경에서 불확실성을 파악하고 해결 방안을 모색하는데 사용하는 툴
- 불확실성의 예
  - 네트워크 지연
  - 서버장애
  - 디스크 오작동
  - 메모리 누수
  - ...
- 카오스 멍키 스프링부트
  - 스프링부트 애플리케이션에 카오스 멍키를 손쉽게 적용해 볼 수 있는 툴
  - 즉, 스프링부트 애플리케이션을 망가트릴 수 있는 툴
- 카오스 멍키 주요 개념

| 공격대상(Watch)                                                                     | 공격 유형(Assaults)                                                                                                       |
|:--------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------|
| @RestController <br> @Controller <br> @Service <br> @Repository <br> @Component | 응답 지연 (Latency Assault) <br> 예외 발생 (Exception Assault) <br> 애플리케이션 종료 (AppKiller Assault) <br> 메모리 누수 (Memory Assault)|

### 관련 의존성
- Chaos-monkey-spring-boot <br>
스프링 부트용 카오스 멍키 제공
- Spring-boot-starter-actuator <br>
스프링 부트 운영 툴로, 런타임 중에 카오스 멍키 설정을 변경할 수 있음 <br>
그밖에도 헬스 체크, 로그 레벨 변경, 매트릭스 데이터 조회 등 다양한 운영 툴로 사용 가능
- 카오스 멍키 활성화 <br>
```
spring.profiles.active=chaos-monkey
```
- 스프링 부트 Actuator 엔드 포인트 활성화 <br>
```
management.endpoint.chaosmonkey.enabled=true
management.endpoints.web.exposure.include=health,info,chaosmonkey
```
