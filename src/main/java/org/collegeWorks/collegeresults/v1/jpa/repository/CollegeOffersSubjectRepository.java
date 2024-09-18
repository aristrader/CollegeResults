package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.CollegeOffersSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeOffersSubjectRepository extends
    JpaRepository<CollegeOffersSubjectEntity, Integer> {

  List<CollegeOffersSubjectEntity> findByCollegeIdAndCourseIdAndSemId(int collegeId, int courseId,
      int semId);

  Optional<CollegeOffersSubjectEntity> findByCollegeIdAndCourseIdAndSemIdAndSubjectOptionalId(
      int collegeId, int courseId, int semId, int subjectOptionalId);
}

