package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v2.dto.TeacherDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.TeacherEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.TeacherRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.TeacherRequestV2;
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
      throw new ServiceException(
          "[TeacherServiceV2] Failed to add teacher due to data integrity issues: "
              + e.getMessage());
    } catch (Exception e) {
      throw new ServiceException("[TeacherServiceV2] Failed to add teacher: " + e.getMessage());
    }
  }

  public TeacherDTOV2 getTeacherById(int teacherId) throws ServiceException {
    try {
      TeacherEntityV2 entity = teacherRepository.findById(teacherId).orElseThrow(
          () -> new ServiceException("[TeacherServiceV2] Teacher not found for ID: " + teacherId));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[TeacherServiceV2] Failed to retrieve teacher: " + e.getMessage());
    }
  }

  public List<TeacherDTOV2> getAllTeachers(int collegeId) throws ServiceException {
    try {
      List<TeacherEntityV2> entities = teacherRepository.findByCollegeId(collegeId);
      if (entities.isEmpty()) {
        throw new ServiceException("[TeacherServiceV2] No teachers found in the database.");
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[TeacherServiceV2] Failed to retrieve teachers: " + e.getMessage());
    }
  }

  public TeacherDTOV2 getTeacherByPhoneNumber(String phoneNumber) throws ServiceException {
    try {
      TeacherEntityV2 entity = teacherRepository.findByPhoneNumber(phoneNumber).orElseThrow(
          () -> new ServiceException(
              "[TeacherServiceV2] Teacher not found for phone number: " + phoneNumber));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[TeacherServiceV2] Failed to retrieve teacher by phone number: " + e.getMessage());
    }
  }

  public TeacherDTOV2 getTeacherByEmail(String email) throws ServiceException {
    try {
      TeacherEntityV2 entity = teacherRepository.findByEmail(email).orElseThrow(
          () -> new ServiceException("[TeacherServiceV2] Teacher not found for email: " + email));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[TeacherServiceV2] Failed to retrieve teacher by email: " + e.getMessage());
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

