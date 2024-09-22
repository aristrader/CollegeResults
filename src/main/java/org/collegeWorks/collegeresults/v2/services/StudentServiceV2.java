package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v2.dto.StudentDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.StudentEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.StudentRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.StudentRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentServiceV2 {

  @Autowired
  private StudentRepositoryV2 studentRepository;

  public Integer addStudent(StudentRequestV2 request) throws ServiceException {
    try {
      StudentEntityV2 entity = convertRequestToEntity(request);
      return studentRepository.save(entity).getStudentId();
    } catch (Exception e) {
      throw new ServiceException("[StudentServiceV2] Failed to add student: " + e.getMessage());
    }
  }

  public List<StudentDTOV2> getAllStudents(Integer courseDetailsId) throws ServiceException {
    try {
      List<StudentEntityV2> entities = studentRepository.findByCourseDetailsId(courseDetailsId);
      if (entities.isEmpty()) {
        throw new ServiceException("[StudentServiceV2] No students found for the given course.");
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (Exception e) {
      throw new ServiceException(
          "[StudentServiceV2] Failed to retrieve students: " + e.getMessage());
    }
  }

  public StudentDTOV2 getStudentByRollNo(Integer courseDetailsId, String rollNo)
      throws ServiceException {
    try {
      StudentEntityV2 entity = studentRepository.findByCourseDetailsIdAndRollNo(courseDetailsId,
          rollNo).orElseThrow(() -> new ServiceException(
          "[StudentServiceV2] Student not found with given roll number."));
      return convertEntityToDTO(entity);
    } catch (Exception e) {
      throw new ServiceException(
          "[StudentServiceV2] Failed to retrieve student: " + e.getMessage());
    }
  }

  public StudentDTOV2 getStudentByEnrollmentNo(Integer courseDetailsId, String enrollmentNo)
      throws ServiceException {
    try {
      StudentEntityV2 entity = studentRepository.findByCourseDetailsIdAndEnrollmentNo(
          courseDetailsId, enrollmentNo).orElseThrow(() -> new ServiceException(
          "[StudentServiceV2] Student not found with given enrollment number."));
      return convertEntityToDTO(entity);
    } catch (Exception e) {
      throw new ServiceException(
          "[StudentServiceV2] Failed to retrieve student: " + e.getMessage());
    }
  }

  private StudentEntityV2 convertRequestToEntity(StudentRequestV2 request) {
    StudentEntityV2 entity = new StudentEntityV2();
    entity.setStudentName(request.getStudentName());
    entity.setFatherName(request.getFatherName());
    entity.setMotherName(request.getMotherName());
    entity.setRollNo(request.getRollNo());
    entity.setEnrollmentNo(request.getEnrollmentNo());
    entity.setCourseDetailsId(request.getCourseDetailsId());
    entity.setPhoto(request.getPhoto());
    return entity;
  }

  private StudentDTOV2 convertEntityToDTO(StudentEntityV2 entity) {
    StudentDTOV2 dto = new StudentDTOV2();
    dto.setStudentId(entity.getStudentId());
    dto.setStudentName(entity.getStudentName());
    dto.setFatherName(entity.getFatherName());
    dto.setMotherName(entity.getMotherName());
    dto.setRollNo(entity.getRollNo());
    dto.setEnrollmentNo(entity.getEnrollmentNo());
    dto.setCourseDetailsId(entity.getCourseDetailsId());
    dto.setPhoto(entity.getPhoto());
    return dto;
  }
}
