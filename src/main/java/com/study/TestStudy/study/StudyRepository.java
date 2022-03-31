package com.study.TestStudy.study;

import com.study.TestStudy.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}