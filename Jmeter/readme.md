## Jmeter

### JMeter 소개
- 성능 측정 및 부하 (load) 테스트 기능을 제공하는 오픈 소스 자바 애플리케이션 <br>
참고: https://jmeter.apache.org/ <br>
- 다양한 형태의 애플리케이션 테스트 지원
  - 웹 - HTTP, HTTPS
  - SOAP / REST 웹 서비스
  - FTP
  - 데이터베이스 (JDBC 사용)
  - Mail (SMTP, POP3, IMAP)
  - ...
- CLI 지원 <br>
CI 또는 CD 툴과 연동할 때 편리함 <br>
UI 사용하는 것보다 메모리 등 시스템 리소스를 적게 사용 <br>
- 주요 개념
  - Thread Group: 한 쓰레드 당 유저 한명
  - Sampler: 어떤 유저가 해야 하는 액션
  - Listener: 응답을 받았을 할 일 (리포팅, 검증, 그래프 그리기 등)
  - Configuration: Sampler 또는 Listener가 사용할 설정 값 (쿠키, JDBC 커넥션 등)
  - Assertion: 응답이 성공적인지 확인하는 방법 (응답 코드, 본문 내용 등)
- 대체제: Gatling, nGrinder

### JMeter 사용
