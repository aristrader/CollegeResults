package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.CourseEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.CourseRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.CourseRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseServiceV1 {

  @Autowired
  CourseRepositoryV1 courseRepository;

  public int insertCourse(CourseRequestV1 courseRequest) throws ServiceException {
    try {
      CourseEntityV1 courseEntityV1 = getCourseEntityFromCourseRequest(courseRequest);
      return courseRepository.save(courseEntityV1).getId();
    } catch (DataIntegrityViolationException ex) {
      log.error("[CourseServiceV1] Data integrity violation occurred : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    }
  }

  public List<CourseEntityV1> getAllCoursesContainingName(String name) throws ServiceException {
    List<CourseEntityV1> courses = courseRepository.findByNameContainingIgnoreCase(name);
    if (courses.isEmpty()) {
      throw new ServiceException("No course found containing the name : " + name);
    }
    return courses;
  }

  public CourseEntityV1 getCourseWithNameAndLength(String name, int length) throws ServiceException {
    return courseRepository.findByNameAndLength(name, length)
        .orElseThrow(() -> new ServiceException("No such course is present in the DB."));
  }

  private CourseEntityV1 getCourseEntityFromCourseRequest(CourseRequestV1 courseRequest) {
    CourseEntityV1 courseEntityV1 = new CourseEntityV1();
    courseEntityV1.setName(courseRequest.getName());
    courseEntityV1.setLength(courseRequest.getLength());
    return courseEntityV1;
  }
}
