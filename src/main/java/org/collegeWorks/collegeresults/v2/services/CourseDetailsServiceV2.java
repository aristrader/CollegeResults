package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.exception.ServiceException.CollegeServiceErrorCodes;
import org.collegeWorks.collegeresults.helper.InputConstraints;
import org.collegeWorks.collegeresults.v2.dto.CourseDetailsDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.CourseDetailsEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.CourseDetailsRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.CourseDetailsRequestV2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseDetailsServiceV2 {

  private final CourseDetailsRepositoryV2 courseRepository;

  public Integer addCourse(CourseDetailsRequestV2 request) throws ServiceException {

    // Validate course name using the Map
    if (!InputConstraints.isValidCourse(request.getCourseName())) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Invalid course name. Allowed values are: BCOM, BSC, BA.",
          CollegeServiceErrorCodes.INVALID_VALUES_FOR_REQUIRED_PARAMETER);
    }

    // Validate semester using the Map
    if (!InputConstraints.isValidSemester(request.getSemester())) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Invalid semester. Allowed values are: 1, 2, 3, 4, 5, 6, 7, 8",
          CollegeServiceErrorCodes.INVALID_VALUES_FOR_REQUIRED_PARAMETER);
    }

    try {
      CourseDetailsEntityV2 entity = convertRequestToEntity(request);
      return courseRepository.save(entity).getId();
    } catch (DataIntegrityViolationException e) {
      // Here you can check the cause to differentiate further if needed
      Throwable cause = e.getCause();
      if (cause instanceof ConstraintViolationException) {
        String message = cause.getMessage();
        if (message.contains("Duplicate entry")) {
          throw new ServiceException(
              "[CourseDetailsServiceV2] Unique or Primary Key constraint violation: " + message,
              CollegeServiceErrorCodes.DUPLICATE_DATA);
        } else if (message.contains("foreign key constraint fails")) {
          throw new ServiceException(
              "[CourseDetailsServiceV2] Foreign Key constraint violation: " + message,
              CollegeServiceErrorCodes.FOREIGN_KEY_CONSTRAINT_VIOLATION);
        } else {
          throw new ServiceException(
              "[CourseDetailsServiceV2] Other constraint violation: " + message,
              CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
        }
      } else {
        throw new ServiceException(
            "[CourseDetailsServiceV2] Data integrity violation: " + e.getMessage(),
            CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
      }
    } catch (Exception e) {
      throw new ServiceException("[CourseDetailsServiceV2] Failed to add course: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<CourseDetailsDTOV2> getAllCoursesByCollegeId(int collegeId) throws ServiceException {
    try {
      List<CourseDetailsEntityV2> entities = courseRepository.findByCollegeId(collegeId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[CourseDetailsServiceV2] No courses were found in the database for the given college id.",
            CollegeServiceErrorCodes.COURSE_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to retrieve courses: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<CourseDetailsDTOV2> getCoursesByCollegeIdAndName(int collegeId, String courseName)
      throws ServiceException {
    try {
      List<CourseDetailsEntityV2> entities = courseRepository.findByCollegeIdAndCourseName(
          collegeId, courseName);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[CourseDetailsServiceV2] No courses were found in the database for the given college id and course name.",
            CollegeServiceErrorCodes.COURSE_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to retrieve courses: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public CourseDetailsDTOV2 getCourseDetails(int collegeId, String courseName, int semester)
      throws ServiceException {
    try {
      CourseDetailsEntityV2 entity = courseRepository.findByCollegeIdAndCourseNameAndSemester(
          collegeId, courseName, semester);
      if (entity == null) {
        throw new ServiceException("[CourseDetailsServiceV2] Course not found.",
            CollegeServiceErrorCodes.COURSE_DETAILS_NOT_FOUND);
      }
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CourseDetailsServiceV2] Failed to retrieve course details: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
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
