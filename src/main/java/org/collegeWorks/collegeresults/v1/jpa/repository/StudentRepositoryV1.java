package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.StudentEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepositoryV1 extends JpaRepository<StudentEntityV1, Integer> {

  Optional<StudentEntityV1> findByCollegeIdAndRollNo(int collegeId, String rollNo);

  Optional<StudentEntityV1> findByCollegeIdAndEnrollmentNo(int collegeId, String enrollmentNo);

  List<StudentEntityV1> findByCollegeIdAndCourseIdAndSemId(int collegeId, int courseId, int semId);
}
