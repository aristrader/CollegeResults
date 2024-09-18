package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.CollegeOffersSubjectEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeOffersSubjectRepositoryV1 extends
    JpaRepository<CollegeOffersSubjectEntityV1, Integer> {

  List<CollegeOffersSubjectEntityV1> findByCollegeIdAndCourseIdAndSemId(int collegeId, int courseId,
      int semId);

  Optional<CollegeOffersSubjectEntityV1> findByCollegeIdAndCourseIdAndSemIdAndSubjectOptionalId(
      int collegeId, int courseId, int semId, int subjectOptionalId);
}

