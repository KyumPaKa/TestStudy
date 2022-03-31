## Docker 테스트

### Testcontainers 소개
- 테스트에서 도커 컨테이너를 실행할 수 있는 라이브러리
- 테스트 실행시 DB를 설정하거나 별도의 프로그램 또는 스크립트를 실행할 필요 없음
- 보다 Production에 가까운 테스트를 만들 수 있음
- 테스트가 느려짐
<br>
참고: https://www.testcontainers.org/

### Testcontainers
- `@Testcontainers` <br>
JUnit 5 확장팩으로 테스트 클래스에 `@Container`를 사용한 필드를 찾아서 컨테이너 라이프사이클 관련 메소드를 실행해줌
- `@Container` <br>
인스턴스 필드에 사용하면 모든 테스트 마다 컨테이너를 재시작 하고, 스태틱 필드에 사용하면 클래스 내부 모든 테스트에서 동일한 컨테이너를 재사용
- 여러 모듈을 제공하는데, 각 모듈은 별도로 설치해야 함
- application.properties 설정 <br>
tc: 를 넣어서 참조 정보 참고하도록 함
```
  spring.datasource.url=jdbc:tc:postgresql:///studytest
  spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver
```

### Testcontainers 기능
- 컨테이너 만들기
```
New GenericContainer(String imageName)
```

- 네트워크
```
withExposedPorts(int...)
getMappedPort(int)
```

- 환경 변수 설정
```
withEnv(key, value)
```

- 명령어 실행
```
withCommand(String cmd...)
```

- 사용할 준비가 됐는지 확인하기
```
waitingFor(Wait)
Wait.forHttp(String url)
Wait.forLogMessage(String message)
```

- Docker 로그 살펴보기
```
getLogs()
followOutput()
```

### 컨테이너 정보를 스프링 테스트에서 참조
- `@ContextConfiguration` <br>
스프링이 제공하는 애노테이션으로, 스프링 테스트 컨텍스트가 사용할 설정 파일 또는 컨텍스트를 커스터마이징할 수 있는 방법을 제공
- `ApplicationContextInitializer` <br>
스프링 ApplicationContext를 프로그래밍으로 초기화 할 때 사용할 수 있는 콜백 인터페이스로, 특정 프로파일을 활성화 하거나, 프로퍼티 소스를 추가하는 등의 작업을 할 수 있음
- TestPropertyValues
테스트용 프로퍼티 소스를 정의할 때 사용
- Environment
스프링 핵심 API로, 프로퍼티와 프로파일을 담당
```
static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of("container.port=" + composeContainer.getServicePort("study-db", 5432))
                .applyTo(context.getEnvironment());
    }
}
```

- 전체 흐름
  - Testcontainer를 사용해서 컨테이너 생성
  - ApplicationContextInitializer를 구현하여 생선된 컨테이너에서 정보를 축출하여 Environment에 넣어준다.
  - @ContextConfiguration을 사용해서 ApplicationContextInitializer 구현체를 등록한다.
  - 테스트 코드에서 Environment, @Value, @ConfigurationProperties 등 다양한 방법으로 해당 프로퍼티를 사용한다.

### Docker Compose 사용
- Docker Compose <br>
여러 컨테이너를 한번에 띄우고 서로 간의 의존성 및 네트워크 등을 설정할 수 있는 방법 <br>
참고: https://docs.docker.com/compose/
- Testcontainser의 docker compose 모듈을 사용할 수 있음
- 특정 서비스 Expose 참조 가능
```
static DockerComposeContainer composeContainer =
        new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
        .withExposedService("study-db", 5432);
```
- Compose 서비스 정보 참조
```
static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of("container.port=" + composeContainer.getServicePort("study-db", 5432))
                .applyTo(context.getEnvironment());
    }
}
```