package org.collegeWorks.collegeresults.v2.jpa.repository;

import org.collegeWorks.collegeresults.v2.jpa.entity.EnrollmentAndMarksEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentAndMarksRepositoryV2 extends JpaRepository<EnrollmentAndMarksEntityV2, Integer> {

  List<EnrollmentAndMarksEntityV2> findByStudentId(Integer studentId);
  List<EnrollmentAndMarksEntityV2> findBySubjectDetailsId(Integer subjectDetailsId);
}
