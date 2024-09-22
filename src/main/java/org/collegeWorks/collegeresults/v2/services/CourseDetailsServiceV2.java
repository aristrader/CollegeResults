package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v2.dto.CourseDetailsDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.CourseDetailsEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.CourseDetailsRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.CourseDetailsRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseDetailsServiceV2 {

  @Autowired
  private CourseDetailsRepositoryV2 courseRepository;

  public Integer addCourse(CourseDetailsRequestV2 request) throws ServiceException {
    try {
      CourseDetailsEntityV2 entity = convertRequestToEntity(request);
      return courseRepository.save(entity).getId();
    } catch (DataIntegrityViolationException e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to add course due to data integrity issues: "
              + e.getMessage());
    } catch (Exception e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to add course: " + e.getMessage());
    }
  }

  public List<CourseDetailsDTOV2> getAllCoursesByCollegeId(int collegeId) throws ServiceException {
    try {
      List<CourseDetailsEntityV2> entities = courseRepository.findByCollegeId(collegeId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[CourseServiceV2] No courses were found in the database for the given college id.");
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to retrieve courses: " + e.getMessage());
    }
  }

  public List<CourseDetailsDTOV2> getCoursesByCollegeIdAndName(int collegeId, String courseName)
      throws ServiceException {
    try {
      List<CourseDetailsEntityV2> entities = courseRepository.findByCollegeIdAndCourseName(
          collegeId, courseName);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[CourseServiceV2] No courses were found in the database for the given college id and course name.");
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to retrieve courses: " + e.getMessage());
    }
  }

  public CourseDetailsDTOV2 getCourseDetails(int collegeId, String courseName, int semester)
      throws ServiceException {
    try {
      CourseDetailsEntityV2 entity = courseRepository.findByCollegeIdAndCourseNameAndSemester(
          collegeId, courseName, semester);
      if (entity == null) {
        throw new ServiceException("[CourseDetailsServiceV2] Course not found.");
      }
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to retrieve course details: " + e.getMessage());
    }
  }

  private CourseDetailsEntityV2 convertRequestToEntity(CourseDetailsRequestV2 request) {
    CourseDetailsEntityV2 entity = new CourseDetailsEntityV2();
    entity.setCollegeId(request.getCollegeId());
    entity.setCourseName(request.getCourseName());
    entity.setSemester(request.getSemester());
    entity.setClassTeacherId(request.getClassTeacherId());
    return entity;
  }

  private CourseDetailsDTOV2 convertEntityToDTO(CourseDetailsEntityV2 entity) {
    CourseDetailsDTOV2 dto = new CourseDetailsDTOV2();
    dto.setCourseId(entity.getId());
    dto.setCollegeId(entity.getCollegeId());
    dto.setCourseName(entity.getCourseName());
    dto.setSemester(entity.getSemester());
    dto.setClassTeacherId(entity.getClassTeacherId());
    return dto;
  }
}
