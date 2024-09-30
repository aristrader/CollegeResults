package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.exception.ServiceException.CollegeServiceErrorCodes;
import org.collegeWorks.collegeresults.v2.dto.TeacherDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.TeacherEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.TeacherRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.TeacherRequestV2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeacherServiceV2 {

  @Autowired
  private TeacherRepositoryV2 teacherRepository;

  public Integer addTeacher(TeacherRequestV2 request) throws ServiceException {
    try {
      TeacherEntityV2 entity = convertRequestToEntity(request);
      return teacherRepository.save(entity).getId();
    } catch (DataIntegrityViolationException e) {
      // Here you can check the cause to differentiate further if needed
      Throwable cause = e.getCause();
      if (cause instanceof ConstraintViolationException) {
        String message = cause.getMessage();
        if (message.contains("Duplicate entry")) {
          throw new ServiceException(
              "[TeacherServiceV2] Unique or Primary Key constraint violation: " + message,
              CollegeServiceErrorCodes.DUPLICATE_DATA);
        } else if (message.contains("foreign key constraint fails")) {
          throw new ServiceException(
              "[TeacherServiceV2] Foreign Key constraint violation: " + message,
              CollegeServiceErrorCodes.FOREIGN_KEY_CONSTRAINT_VIOLATION);
        } else {
          throw new ServiceException("[TeacherServiceV2] Other constraint violation: " + message,
              CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
        }
      } else {
        throw new ServiceException("[TeacherServiceV2] Data integrity violation: " + e.getMessage(),
            CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
      }
    } catch (Exception e) {
      throw new ServiceException("[TeacherServiceV2] Failed to add teacher: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public TeacherDTOV2 getTeacherById(int teacherId) throws ServiceException {
    try {
      TeacherEntityV2 entity = teacherRepository.findById(teacherId).orElseThrow(
          () -> new ServiceException("[TeacherServiceV2] Teacher not found for ID: " + teacherId,
              CollegeServiceErrorCodes.TEACHER_DETAILS_NOT_FOUND));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException("[TeacherServiceV2] Failed to retrieve teacher: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<TeacherDTOV2> getAllTeachers(int collegeId) throws ServiceException {
    try {
      List<TeacherEntityV2> entities = teacherRepository.findByCollegeId(collegeId);
      if (entities.isEmpty()) {
        throw new ServiceException("[TeacherServiceV2] No teachers found in the database.",
            CollegeServiceErrorCodes.TEACHER_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[TeacherServiceV2] Failed to retrieve teachers: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public TeacherDTOV2 getTeacherByPhoneNumber(String phoneNumber) throws ServiceException {
    try {
      TeacherEntityV2 entity = teacherRepository.findByPhoneNumber(phoneNumber).orElseThrow(
          () -> new ServiceException(
              "[TeacherServiceV2] Teacher not found for phone number: " + phoneNumber,
              CollegeServiceErrorCodes.TEACHER_DETAILS_NOT_FOUND));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[TeacherServiceV2] Failed to retrieve teacher by phone number: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public TeacherDTOV2 getTeacherByEmail(String email) throws ServiceException {
    try {
      TeacherEntityV2 entity = teacherRepository.findByEmail(email).orElseThrow(
          () -> new ServiceException("[TeacherServiceV2] Teacher not found for email: " + email,
              CollegeServiceErrorCodes.TEACHER_DETAILS_NOT_FOUND));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[TeacherServiceV2] Failed to retrieve teacher by email: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  private TeacherEntityV2 convertRequestToEntity(TeacherRequestV2 request) {
    TeacherEntityV2 entity = new TeacherEntityV2();
    entity.setCollegeId(request.getCollegeId());
    entity.setName(request.getName());
    entity.setExperience(request.getExperience());
    entity.setEmail(request.getEmail());
    entity.setPhoneNumber(request.getPhoneNumber());
    entity.setSpecialization(request.getSpecialization());
    entity.setPhoto(request.getPhoto());
    return entity;
  }

  private TeacherDTOV2 convertEntityToDTO(TeacherEntityV2 entity) {
    TeacherDTOV2 dto = new TeacherDTOV2();
    dto.setTeacherId(entity.getId());
    dto.setCollegeId(entity.getCollegeId());
    dto.setName(entity.getName());
    dto.setExperience(entity.getExperience());
    dto.setEmail(entity.getEmail());
    dto.setPhoneNumber(entity.getPhoneNumber());
    dto.setSpecialization(entity.getSpecialization());
    dto.setPhoto(entity.getPhoto());
    return dto;
  }
}

