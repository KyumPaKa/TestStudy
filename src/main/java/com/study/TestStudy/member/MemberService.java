package com.study.TestStudy.member;

import com.study.TestStudy.domain.Member;
import com.study.TestStudy.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
