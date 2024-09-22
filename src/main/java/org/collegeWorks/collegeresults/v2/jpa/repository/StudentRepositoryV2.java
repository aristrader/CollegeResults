package org.collegeWorks.collegeresults.v2.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v2.jpa.entity.StudentEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositoryV2 extends JpaRepository<StudentEntityV2, Integer> {

  List<StudentEntityV2> findByCourseDetailsId(Integer courseDetailsId);

  Optional<StudentEntityV2> findByCourseDetailsIdAndRollNo(Integer courseDetailsId, String rollNo);

  Optional<StudentEntityV2> findByCourseDetailsIdAndEnrollmentNo(Integer courseDetailsId,
      String enrollmentNo);
}
