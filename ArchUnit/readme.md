## 아키텍처 테스트(ArchUnit)

### ArchUnit 소개
- 애플리케이션의 아키텍처를 테스트 할 수 있는 오픈 소스 라이브러리로, 패키지, 클래스, 레이어, 슬라이스 간의 의존성을 확인할 수 있는 기능을 제공
- 아키텍처 테스트 유즈 케이스
  - A 라는 패키지가 B (또는 C, D) 패키지에서만 사용 되고 있는지 확인 가능
  - *Serivce라는 이름의 클래스들이 *Controller 또는 *Service라는 이름의 클래스에서만 참조하고 있는지 확인
  - *Service라는 이름의 클래스들이 ..service.. 라는 패키지에 들어있는지 확인
  - A라는 애노테이션을 선언한 메소드만 특정 패키지 또는 특정 애노테이션을 가진 클래스를 호출하고 있는지 확인
  - 특정한 스타일의 아키텍처를 따르고 있는지 확인
<br>

참조: https://www.archunit.org/

- 주요사용법
    1. 특정 패키지에 해당하는 클래스를 (바이트코드를 통해) 읽음
    2. 확인할 규칙을 정의
    3. 읽어들인 클래스들이 그 규칙을 잘 따르는지 확인
```
@Test
public void Services_should_only_be_accessed_by_Controllers() {
    // 1.
    JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mycompany.myapp");

    // 2.
    ArchRule myRule = classes()
        .that().resideInAPackage("..service..")
        .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

    // 3.
    myRule.check(importedClasses);
}
```
- JUnit 5 확장팩 제공
  - @AnalyzeClasses: 클래스를 읽어들여서 확인할 패키지 설정
  - @ArchTest: 확인할 규칙 정의
- 패키지 의존성 확인
```
@AnalyzeClasses(packagesOf = App.class)
public class ArchTests {

    @ArchTest
    ArchRule domainPackageRule = classes().that().resideInAPackage("..domain..")
    .should().onlyBeAccessed().byClassesThat()
    .resideInAnyPackage("..study..", "..member..", "..domain..");

    @ArchTest
    ArchRule memberPackageRule = noClasses().that().resideInAPackage("..domain..")
    .should().accessClassesThat().resideInAPackage("..member..");

    @ArchTest
    ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
    .should().accessClassesThat().resideInAnyPackage("..study..");

    @ArchTest
    ArchRule freeOfCycles = slices().matching("..inflearnthejavatest.(*)..")
    .should().beFreeOfCycles();
}
```
- 클래스 의존성 확인
```
@AnalyzeClasses(packagesOf = App.class)
public class ArchClassTests {

    @ArchTest
    ArchRule controllerClassRule = classes().that().haveSimpleNameEndingWith("Controller")
            .should().accessClassesThat().haveSimpleNameEndingWith("Service")
            .orShould().accessClassesThat().haveSimpleNameEndingWith("Repository");

    @ArchTest
    ArchRule repositoryClassRule = noClasses().that().haveSimpleNameEndingWith("Repository")
            .should().accessClassesThat().haveSimpleNameEndingWith("Service");

    @ArchTest
    ArchRule studyClassesRule = classes().that().haveSimpleNameStartingWith("Study")
            .and().areNotEnums()
            .and().areNotAnnotatedWith(Entity.class)
            .should().resideInAnyPackage("..study..");

}
```