package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.StudentDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.StudentEntity;
import org.collegeWorks.collegeresults.v1.jpa.repository.StudentRepository;
import org.collegeWorks.collegeresults.v1.model.StudentRequestForMultipleStudents;
import org.collegeWorks.collegeresults.v1.model.StudentRequestSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  public int insertStudent(StudentRequestSingle studentRequestSingle) throws ServiceException {
    try {
      StudentEntity studentEntity = getStudentEntityFromStudentRequestSingle(studentRequestSingle);
      return studentRepository.save(studentEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      throw new ServiceException(
          "Failed to insert student due to data integrity violation: " + ex.getMessage());
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert student: " + ex.getMessage());
    }
  }

  public List<Integer> insertStudents(StudentRequestForMultipleStudents multipleStudents)
      throws ServiceException {
    try {
      List<StudentEntity> studentEntities = multipleStudents.getStudentDetailsList().stream().map(
              details -> getStudentEntityFromStudentDetails(details, multipleStudents.getCollegeId(),
                  multipleStudents.getCourseId(), multipleStudents.getSemId()))
          .collect(Collectors.toList());

      List<StudentEntity> savedEntities = studentRepository.saveAll(studentEntities);
      return savedEntities.stream().map(StudentEntity::getId).collect(Collectors.toList());
    } catch (DataIntegrityViolationException ex) {
      throw new ServiceException(
          "Failed to insert students due to data integrity violation: " + ex.getMessage());
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert students: " + ex.getMessage());
    }
  }

  public StudentDTO getStudentByCollegeIdAndRollNo(int collegeId, String rollNo)
      throws ServiceException {
    StudentEntity studentEntity = studentRepository.findByCollegeIdAndRollNo(collegeId, rollNo)
        .orElseThrow(() -> new ServiceException(
            "No student found with the provided college ID and roll number."));
    return convertEntityToDTO(studentEntity);
  }

  public StudentDTO getStudentByCollegeIdAndEnrollmentNo(int collegeId, String enrollmentNo)
      throws ServiceException {
    StudentEntity studentEntity = studentRepository.findByCollegeIdAndEnrollmentNo(collegeId,
        enrollmentNo).orElseThrow(() -> new ServiceException(
        "No student found with the provided college ID and enrollment number."));
    return convertEntityToDTO(studentEntity);
  }

  public List<StudentDTO> getStudentsByCollegeCourseAndSemester(int collegeId, int courseId,
      int semId) throws ServiceException {
    List<StudentEntity> students = studentRepository.findByCollegeIdAndCourseIdAndSemId(collegeId,
        courseId, semId);
    if (students.isEmpty()) {
      throw new ServiceException(
          "No students found for the provided college, course, and semester.");
    }
    return students.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
  }

  private StudentEntity getStudentEntityFromStudentRequestSingle(
      StudentRequestSingle studentRequestSingle) {
    StudentEntity studentEntity = new StudentEntity();
    studentEntity.setName(studentRequestSingle.getName());
    studentEntity.setRollNo(studentRequestSingle.getRollNo());
    studentEntity.setEnrollmentNo(studentRequestSingle.getEnrollmentNo());
    studentEntity.setCollegeId(studentRequestSingle.getCollegeId());
    studentEntity.setCourseId(studentRequestSingle.getCourseId());
    studentEntity.setSemId(studentRequestSingle.getSemId());
    studentEntity.setFatherName(studentRequestSingle.getFatherName());
    studentEntity.setMotherName(studentRequestSingle.getMotherName());
    return studentEntity;
  }

  private StudentEntity getStudentEntityFromStudentDetails(
      StudentRequestForMultipleStudents.StudentDetails studentDetails, int collegeId, int courseId,
      int semId) {
    StudentEntity studentEntity = new StudentEntity();
    studentEntity.setName(studentDetails.getName());
    studentEntity.setRollNo(studentDetails.getRollNo());
    studentEntity.setEnrollmentNo(studentDetails.getEnrollmentNo());
    studentEntity.setCollegeId(collegeId);
    studentEntity.setCourseId(courseId);
    studentEntity.setSemId(semId);
    studentEntity.setFatherName(studentDetails.getFatherName());
    studentEntity.setMotherName(studentDetails.getMotherName());
    return studentEntity;
  }

  public StudentDTO convertEntityToDTO(StudentEntity studentEntity) {
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.setId(studentEntity.getId());
    studentDTO.setName(studentEntity.getName());
    studentDTO.setFatherName(studentEntity.getFatherName());
    studentDTO.setMotherName(studentEntity.getMotherName());
    studentDTO.setRollNo(studentEntity.getRollNo());
    studentDTO.setEnrollmentNo(studentEntity.getEnrollmentNo());
    studentDTO.setPhoto(studentEntity.getPhoto());
    studentDTO.setCollegeId(studentEntity.getCollegeId());
    studentDTO.setCourseId(studentEntity.getCourseId());
    studentDTO.setSemId(studentEntity.getSemId());
    return studentDTO;
  }
}
