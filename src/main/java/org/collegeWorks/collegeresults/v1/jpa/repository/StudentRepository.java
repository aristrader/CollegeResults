package org.collegeWorks.collegeresults.v1.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.collegeWorks.collegeresults.v1.jpa.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

  Optional<StudentEntity> findByCollegeIdAndRollNo(int collegeId, String rollNo);

  Optional<StudentEntity> findByCollegeIdAndEnrollmentNo(int collegeId, String enrollmentNo);

  List<StudentEntity> findByCollegeIdAndCourseIdAndSemId(int collegeId, int courseId, int semId);
}
