package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.CourseEntity;
import org.collegeWorks.collegeresults.v1.jpa.repository.CourseRepository;
import org.collegeWorks.collegeresults.v1.model.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseService {

  @Autowired
  CourseRepository courseRepository;

  public int insertCourse(CourseRequest courseRequest) throws ServiceException {
    try {
      CourseEntity courseEntity = getCourseEntityFromCourseRequest(courseRequest);
      return courseRepository.save(courseEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      log.error("[CourseService] Data integrity violation occurred : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    }
  }

  public List<CourseEntity> getAllCoursesContainingName(String name) throws ServiceException {
    List<CourseEntity> courses = courseRepository.findByNameContainingIgnoreCase(name);
    if (courses.isEmpty()) {
      throw new ServiceException("No course found containing the name : " + name);
    }
    return courses;
  }

  public CourseEntity getCourseWithNameAndLength(String name, int length) throws ServiceException {
    return courseRepository.findByNameAndLength(name, length)
        .orElseThrow(() -> new ServiceException("No such course is present in the DB."));
  }

  private CourseEntity getCourseEntityFromCourseRequest(CourseRequest courseRequest) {
    CourseEntity courseEntity = new CourseEntity();
    courseEntity.setName(courseRequest.getName());
    courseEntity.setLength(courseRequest.getLength());
    return courseEntity;
  }
}
