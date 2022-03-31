package com.study.TestStudy;

import com.study.TestStudy.domain.Member;
import com.study.TestStudy.domain.Study;
import com.study.TestStudy.member.MemberService;
import com.study.TestStudy.study.StudyRepository;
import com.study.TestStudy.study.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    @Test
    void createStudyServiceNoMock() {
        MemberService memberService = new MemberService() {
            @Override
            public Optional<Member> findById(Long memberId) {
                return Optional.empty();
            }

            @Override
            public void validate(Long memberId) {

            }

            @Override
            public void notify(Study newstudy) {

            }

            @Override
            public void notify(Member member) {

            }
        };

        StudyRepository studyRepository = new StudyRepository() {
            @Override
            public List<Study> findAll() {
                return null;
            }

            @Override
            public List<Study> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Study> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends Study> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Study> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Study> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Study> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Study getOne(Long aLong) {
                return null;
            }

            @Override
            public Study getById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Study> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Study> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Study> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Study> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Study> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Study entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Study> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Study> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Study> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Study> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Study> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Study, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    void createStudyService() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyServiceMockAnnotation() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    void createStudyServiceMockAnnotationParameter(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);

        Optional<Member> optionalMember = memberService.findById(1L);
        assertNotNull(optionalMember);

        memberService.validate(2L);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("abc@email.com");

//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(memberService.findById(any())).thenReturn(Optional.of(member));
//        when(memberService.findById(any())).thenThrow(new RuntimeException());

        Study study = new Study(10, "java");

        Optional<Member> sampleMember = memberService.findById(1L);
        assertEquals("abc@email.com", sampleMember.get().getEmail());

        studyService.createNewStudy(1L, study);

        assertNotNull(studyService);

        doThrow(new RuntimeException()).when(memberService).validate(1L);
        assertThrows(RuntimeException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty())
        ;

        sampleMember = memberService.findById(1L);
        assertEquals("abc@email.com", sampleMember.get().getEmail());
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });
        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    void sampleTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        Member member = new Member();
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Optional<Member> optionalMember = memberService.findById(1L);
        assertEquals(member, optionalMember.get());

        Study study = new Study(10, "테스트");
        when(studyRepository.save(study)).thenReturn(study);
        assertEquals(study, studyRepository.save(study));
    }

    @Test
    void verifyTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        Member member = new Member();
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Optional<Member> optionalMember = memberService.findById(1L);
        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "테스트");
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        verify(memberService, times(1)).notify(study);
//        verifyNoMoreInteractions(memberService);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }

    @Test
    void bddTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        // Given
        Member member = new Member();
        Study study = new Study(10, "테스트");

        Optional<Member> optionalMember = memberService.findById(1L);
        StudyService studyService = new StudyService(memberService, studyRepository);

//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        given(memberService.findById(1L)).willReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.createNewStudy(1L, study);

        // Then
//        verify(memberService, times(1)).notify(study);
        then(memberService).should(times(1)).notify(study);
        then(memberService).should(times(1)).notify(member);
//        verifyNoMoreInteractions(memberService);
//        then(memberService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더자바, 테스트");
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.openStudy(study);

        // Then
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should().notify(study);
    }
}
