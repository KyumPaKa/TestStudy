## Mockito

### 소개
- Mock
진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체
- Mockito
Mock 객체를 쉽게 만들고 관리하고 검증할 수  있는 방법
- 대체제: EasyMock, JMock

### Mock 객체 생성 방법
- Mockito.mock() 메소드로 만드는 방법
```
MemberService memberService = Mockito.mock(MemberService.class);
StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
```
- `@Mock` 어노테이션으로 만드는 방법
Junit5 extension으로 MockitoExtension을 사용해야 함 <br>
  - 필드 생성방법
  - 메소드 매개변수
```
@Mock
MemberService memberService;

@Mock
StudyRepository studyRepository;
```
```
void createStudyServiceMockAnnotationParameter(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
    StudyService studyService = new StudyService(memberService, studyRepository);
    Assertions.assertNotNull(studyService);
}
```

### Mock 객체 Stubbing
- Mock 객체의 행동
  - null 을 리턴(Optional 타입은 Optional.empty 리턴)
  - Primitive 타입은 기본 Primitive 값
  - 콜렉션은 비어있는 콜렉션
  - void 메소드는 예외를 던지지 않고 아무런 일도 발생하지 않음

- `when()` 메소드를 통해 상황을 가정하고 `then..(), doThrow()` 메소드를 통해 결과, 에러를 가정
```
when(memberService.findById(any())).thenReturn(Optional.of(member));

doThrow(new RuntimeException()).when(memberService).validate(1L);
```
- 메소드가 동일한 매개변수로 여러번 호출될 때 각기 다르게 행동하도록 조작할 수 있음
```
when(memberService.findById(any()))
        .thenReturn(Optional.of(member))
        .thenThrow(new RuntimeException())
        .thenReturn(Optional.empty())
;
```

### Mock 객체 확인
Mock 객체가 어떻게 사용 됐는지 확인
- 특정 메소드가 특정 매개변수로 몇번 호출 됐는지, 최소 한번은 호출 됐는지, 전혀 호출되지 않았는지
```
verify(memberService, times(1)).notify(study);
verify(memberService, never()).validate(any());
```
- 어떤 순서로 호출됐는지
```
InOrder inOrder = inOrder(memberService);
inOrder.verify(memberService).notify(study);
inOrder.verify(memberService).notify(member);
```
- 특정 시간 이내에 호출됐는지
```
verify(memberService, timeout(100)).notify(study);
verify(memberService, timeout(100).time(1)).notify(study);
verify(memberService, timeout(100).atLeast(1)).notify(study);
```
- 특정 시점에 아무일도 발생하지 않았는지
```
verifyNoMoreInteractions(memberService);
```

### Mockito BDD 스타일 API
BDD: 애플리케이션이 어떻게 **행동**해야하는지에 대한 공통된 이해를 구성하는 방법, TDD에서 창안한 방법
- 행동에 대한 스펙
  - Title
  - Narrative
    - As a / I want / so that
  - Acceptance criteria
    - Given / When Then
- Mockito는 BddMockito라는 클래스를 통해 BDD 스타일의 API를 제공
```
when(memberService.findById(1L)).thenReturn(Optional.of(member));
given(memberService.findById(1L)).willReturn(Optional.of(member));

when(studyRepository.save(study)).thenReturn(study);
given(studyRepository.save(study)).willReturn(study);
```
```
verify(memberService, times(1)).notify(study);
then(memberService).should(times(1)).notify(study);

verifyNoMoreInteractions(memberService);
then(memberService).shouldHaveNoMoreInteractions();
```
