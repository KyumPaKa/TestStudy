## Junit5

### Junit5 구성
- JUnit Platform, JUnit Jupiter, JUnit Vintage 모듈로 구성
- 대체제: TestNG, Spock, ...

|       이름        | 내용                                                                                           |
|:---------------:|:---------------------------------------------------------------------------------------------|
| JUnit Platform  | JVM 기반 테스팅 프레임워크 실행을 위한 기반 모듈<br/>플랫폼에서 실행되는 테스트 프레임워크 개발을 위한 TestEngine API를 정의(인터페이스)<br> |
|  JUnit Jupiter  | JUnit 5 버전에서 테스트 코드를 작성하기 위한 프로그래밍 모델, 확장 모델을 지원하는 모듈<br>테스트 작성, 확장을 위한 JUnit Jupiter API(junit-jupiter-api)모듈과 테스트 실행을 위한 TestEngine 구현(junit-jupiter-engine) 모듈이 분리                                                                                         |
|  JUnit Vintage  | JUnit 3, JUnit 4 버전으로 작성된 테스트 코드 실행 시 사용되는 모듈                                                |

### Junit4 vs Junit5 비교
|       구분       |  Junit4  |   Junit5    |
|:--------------:|:--------:|:-----------:|
|     JDK버전      | Java5 이상 |  Java8 이상   |
|  Architecture  |  단일Jar   | 3개의 하위 프로젝트 |

### Anotation 정리
- @Test <br>
테스트 대상 지정
- @BeforeAll <br>
전체 테스트 실행 전 1회 실행
- @AfterAll <br>
전체 테스트 실행 후 1회 실행
- @BeforeEach <br>
각 테스트 실행 전마다 실행
- @AfterEach <br>
각 테스트 실행 후마다 실행
- @Disabled <br>
`@Test` 가 있어도 테스트 실행 대상 제외
- @DisplayNameGeneration <br>
기본 구현체 DisplayNameGenerator 를 지정하여 메서드명을 주어진 조건에 따라 자동으로 변경
  - DisplayNameGenerator클래스 종류<br>
  DisplayNameGenerator.Standard, DisplayNameGenerator.Simple, DisplayNameGenerator.ReplaceUnderscores, DisplayNameGenerator.IndicativeSentences
- @DisplayName <br>
테스트 이름 직접표기, `@DisplayNameGeneration` 보다 우선순위가 높음
- @EnabledOnOs <br>
제시된 OS에 따라 테스트 실행
- @DisabledOnOs <br>
제시된 OS에 따라 테스트 미실행
- @EnabledOnJre <br>
제시된 JRE에 따라 테스트 실행
- @DisabledOnJre <br>
제시된 JRE에 따라 테스트 미실행
- @EnabledIfEnvironmentVariable <br>
제시된 named에 맞는 matches에 따라 테스트 실행
- @Tag <br>
테스트 환경에 정의된 조건에 따라 테스트 실행
- @RepeatedTest <br>
어노테이션 파라미터를 통해 반복 횟수를 지정하여 테스트 실행 <br>
`RepetitionInfo` 객체를 통해 메서드 파라미터를 받아서 현재반복횟수, 전체반복횟수를 제공받을 수 있음<br>
{displayName} : 정의된 테스트 이름
{currentRepetition} : 현재반복횟수
{totalRepetitions} : 전체반복횟수
- @ParameterizedTest
`@ValueSource`를 통해 파라미터를 전달 가능 <br>
{displayName} : 정의된 테스트 이름
{index} : 제공된 파라미터의 인덱스
{숫자} : `@ValueSource`를 통해 제공된 파라미터 값

- 어노테이션을 커스텀하여 일괄 적용가능함

### Assertions 메소드 정리
- assertEquals(expected, actual) <br>
실제 값이 기대한 값과 같은지 확인
- assertNotNull(actual) <br>
값이 null이 아닌지 확인
- assertTrue(boolean) <br>
다음 조건이 참(true)인지 확인
- assertAll(executables...) <br>
모든 조건 확인
- assertThrows(expectedType, executable) <br>
예외 발생 확인
- assertTimeout(duration, executable) <br>
특정 시간 안에 실행이 완료되는지 확인
- 등등 기타함수가 다양하게 존재..

AssertJ, Hemcrest, Truth 등의 라이브러리를 활용할 수도 있음

### AssumeTrue 메소드 정리
- assumeTrue <br>
파라미터 값이 true인 경우 이후 테스트 진행
- assumingThat <br>
파라미터 값이 true인 경우 정의된 함수 실행
- 등등 기타함수가 다양하게 존재..

------------
