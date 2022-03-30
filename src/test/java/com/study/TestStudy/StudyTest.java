package com.study.TestStudy;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ExtendWith(FindSlowTestExtension.class)
public class StudyTest {

    int value = 1;

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

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
    void create_new_study_again() throws InterruptedException {
        Thread.sleep(1005L);
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
//    @NullSource
//    @EmptySource
//    @NullAndEmptySource
    void parameterTest(String message) {
        System.out.println(message);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30})
    void parameterConvertTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

        static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "can not convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @ParameterizedTest
    @CsvSource({"10, '자바'", "20, '스프링'"})
    void objectConvertTest(Integer limit, String name) {
        System.out.println(new Study(limit, name));
    }

    @ParameterizedTest
    @CsvSource({"10, '자바'", "20, '스프링'"})
    void objectConvertTest2(ArgumentsAccessor argumentsAccessor) {
        System.out.println(new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1)));
    }

    @ParameterizedTest
    @CsvSource({"10, '자바'", "20, '스프링'"})
    void objectConvertTest3(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

    @Test
    @Order(2)
    void lifecycleTest1() {
        System.out.println(value++);
    }

    @Test
    @Order(1)
    void lifecycleTest2() {
        System.out.println(value++);
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
