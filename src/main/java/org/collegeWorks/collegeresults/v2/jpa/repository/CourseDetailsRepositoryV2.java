package org.collegeWorks.collegeresults.v2.jpa.repository;

import java.util.List;
import org.collegeWorks.collegeresults.v2.jpa.entity.CourseDetailsEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDetailsRepositoryV2 extends JpaRepository<CourseDetailsEntityV2, Integer> {

  List<CourseDetailsEntityV2> findByCollegeId(Integer collegeId);

  List<CourseDetailsEntityV2> findByCollegeIdAndCourseName(Integer collegeId, String courseName);

  CourseDetailsEntityV2 findByCollegeIdAndCourseNameAndSemester(Integer collegeId,
      String courseName, Integer semester);
}
