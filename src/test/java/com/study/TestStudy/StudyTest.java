package com.study.TestStudy;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudyTest {

    @Test
    @DisplayName("스터디")
    void create_new_study() {
        Study study = new Study(10);
        assertNotNull(study);
        System.out.println("create_new_study");
        assertEquals(StudyStatus.Draft, study.getStatus(), "스터디를 처음만들면 상태값이 DRAFT여야 한다.");
        assertEquals(StudyStatus.Draft, study.getStatus(), new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음만들면 상태값이 DRAFT여야 한다.";
            }
        });
        assertEquals(StudyStatus.Draft, study.getStatus(), () -> "스터디를 처음만들면 상태값이 DRAFT여야 한다.");
        assertTrue(1 < 2);
        assertTrue(study.getLimit() > 0, "스터디 최대 참석인원은 0보다 커야한다.");
    }

//    @Disabled
    @Test
    @DisplayName("스터디 다시")
    void create_new_study_again() {
        System.out.println("create_new_study_again");
        Study study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.Draft, study.getStatus(), () -> "스터디를 처음만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석인원은 0보다 커야한다.")
        );
    }

    @Test
    @DisplayName("에러발생 테스트")
    void create_error_test() {
        System.out.println("create_error_test");
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Study(-1));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야합니다.", message);
    }

    @Test
    @DisplayName("타임아웃 테스트")
    void create_timeout_test() {
        System.out.println("create_timeout_test");
        assertTimeout(Duration.ofMillis(500), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }

    @Test
    @DisplayName("타임아웃즉시종료 테스트")
    void create_timeoutPreemptively_test() {
        System.out.println("create_timeoutPreemptively_test");
        assertTimeoutPreemptively(Duration.ofMillis(500), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }

//    @Test
    @DisplayName("조건에 따른 테스트")
//    @EnabledOnOs(OS.MAC)
//    @DisabledOnOs(OS.WINDOWS)
//    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
//    @DisabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
//    @EnabledIfEnvironmentVariable(named = "env", matches = "LOCAL")
//    @Tag("fast")
    @FastTest
    void option_test() {
        String env = "LOCAL";
        assumeTrue("LOCAL".equals(env));
        assumingThat("LOCAL".equals(env), () -> {
            System.out.println("LOCAL");
            Study study = new Study(10);
            org.assertj.core.api.Assertions.assertThat(study.getLimit()).isGreaterThan(0);
        });

        assumingThat("DEV".equals(env), () -> {
            System.out.println("DEV");
            Study study = new Study(10);
            org.assertj.core.api.Assertions.assertThat(study.getLimit()).isGreaterThan(0);
        });
    }

    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatedTest(RepetitionInfo info) {
        System.out.println("test " + info.getCurrentRepetition() + " / " + info.getTotalRepetitions());
    }

    @DisplayName("파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "너무", "좋아요"})
    void parameterTest(String message) {
        System.out.println(message);
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }

}
