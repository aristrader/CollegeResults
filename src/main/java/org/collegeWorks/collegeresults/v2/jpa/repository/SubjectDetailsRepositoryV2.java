package org.collegeWorks.collegeresults.v2.jpa.repository;

import java.util.List;
import org.collegeWorks.collegeresults.v2.jpa.entity.SubjectDetailsEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectDetailsRepositoryV2 extends JpaRepository<SubjectDetailsEntityV2, Integer> {

  List<SubjectDetailsEntityV2> findByCourseDetailsId(int courseDetailsId);

  List<SubjectDetailsEntityV2> findByCourseDetailsIdAndSubjectType(int courseDetailsId,
      String subjectType);

  List<SubjectDetailsEntityV2> findByCourseDetailsIdAndSubjectTypeAndOptionsName(
      int courseDetailsId, String subjectType, String optionsName);

  List<SubjectDetailsEntityV2> findBySubjectTeacherId(int teacherId);
}
