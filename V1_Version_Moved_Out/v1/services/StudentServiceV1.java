package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.StudentDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.StudentEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.StudentRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.StudentRequestForMultipleStudentsV1;
import org.collegeWorks.collegeresults.v1.model.StudentRequestSingleV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentServiceV1 {

  @Autowired
  private StudentRepositoryV1 studentRepository;

  public int insertStudent(StudentRequestSingleV1 studentRequestSingle) throws ServiceException {
    try {
      StudentEntityV1 studentEntityV1 = getStudentEntityFromStudentRequestSingle(studentRequestSingle);
      return studentRepository.save(studentEntityV1).getId();
    } catch (DataIntegrityViolationException ex) {
      throw new ServiceException(
          "Failed to insert student due to data integrity violation: " + ex.getMessage());
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert student: " + ex.getMessage());
    }
  }

  public List<Integer> insertStudents(StudentRequestForMultipleStudentsV1 multipleStudents)
      throws ServiceException {
    try {
      List<StudentEntityV1> studentEntities = multipleStudents.getStudentDetailsList().stream().map(
              details -> getStudentEntityFromStudentDetails(details, multipleStudents.getCollegeId(),
                  multipleStudents.getCourseId(), multipleStudents.getSemId()))
          .collect(Collectors.toList());

      List<StudentEntityV1> savedEntities = studentRepository.saveAll(studentEntities);
      return savedEntities.stream().map(StudentEntityV1::getId).collect(Collectors.toList());
    } catch (DataIntegrityViolationException ex) {
      throw new ServiceException(
          "Failed to insert students due to data integrity violation: " + ex.getMessage());
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert students: " + ex.getMessage());
    }
  }

  public StudentDTOV1 getStudentByCollegeIdAndRollNo(int collegeId, String rollNo)
      throws ServiceException {
    StudentEntityV1 studentEntityV1 = studentRepository.findByCollegeIdAndRollNo(collegeId, rollNo)
        .orElseThrow(() -> new ServiceException(
            "No student found with the provided college ID and roll number."));
    return convertEntityToDTO(studentEntityV1);
  }

  public StudentDTOV1 getStudentByCollegeIdAndEnrollmentNo(int collegeId, String enrollmentNo)
      throws ServiceException {
    StudentEntityV1 studentEntityV1 = studentRepository.findByCollegeIdAndEnrollmentNo(collegeId,
        enrollmentNo).orElseThrow(() -> new ServiceException(
        "No student found with the provided college ID and enrollment number."));
    return convertEntityToDTO(studentEntityV1);
  }

  public List<StudentDTOV1> getStudentsByCollegeCourseAndSemester(int collegeId, int courseId,
      int semId) throws ServiceException {
    List<StudentEntityV1> students = studentRepository.findByCollegeIdAndCourseIdAndSemId(collegeId,
        courseId, semId);
    if (students.isEmpty()) {
      throw new ServiceException(
          "No students found for the provided college, course, and semester.");
    }
    return students.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
  }

  private StudentEntityV1 getStudentEntityFromStudentRequestSingle(
      StudentRequestSingleV1 studentRequestSingle) {
    StudentEntityV1 studentEntityV1 = new StudentEntityV1();
    studentEntityV1.setName(studentRequestSingle.getName());
    studentEntityV1.setRollNo(studentRequestSingle.getRollNo());
    studentEntityV1.setEnrollmentNo(studentRequestSingle.getEnrollmentNo());
    studentEntityV1.setCollegeId(studentRequestSingle.getCollegeId());
    studentEntityV1.setCourseId(studentRequestSingle.getCourseId());
    studentEntityV1.setSemId(studentRequestSingle.getSemId());
    studentEntityV1.setFatherName(studentRequestSingle.getFatherName());
    studentEntityV1.setMotherName(studentRequestSingle.getMotherName());
    return studentEntityV1;
  }

  private StudentEntityV1 getStudentEntityFromStudentDetails(
      StudentRequestForMultipleStudentsV1.StudentDetails studentDetails, int collegeId, int courseId,
      int semId) {
    StudentEntityV1 studentEntityV1 = new StudentEntityV1();
    studentEntityV1.setName(studentDetails.getName());
    studentEntityV1.setRollNo(studentDetails.getRollNo());
    studentEntityV1.setEnrollmentNo(studentDetails.getEnrollmentNo());
    studentEntityV1.setCollegeId(collegeId);
    studentEntityV1.setCourseId(courseId);
    studentEntityV1.setSemId(semId);
    studentEntityV1.setFatherName(studentDetails.getFatherName());
    studentEntityV1.setMotherName(studentDetails.getMotherName());
    return studentEntityV1;
  }

  public StudentDTOV1 convertEntityToDTO(StudentEntityV1 studentEntityV1) {
    StudentDTOV1 studentDTO = new StudentDTOV1();
    studentDTO.setId(studentEntityV1.getId());
    studentDTO.setName(studentEntityV1.getName());
    studentDTO.setFatherName(studentEntityV1.getFatherName());
    studentDTO.setMotherName(studentEntityV1.getMotherName());
    studentDTO.setRollNo(studentEntityV1.getRollNo());
    studentDTO.setEnrollmentNo(studentEntityV1.getEnrollmentNo());
    studentDTO.setPhoto(studentEntityV1.getPhoto());
    studentDTO.setCollegeId(studentEntityV1.getCollegeId());
    studentDTO.setCourseId(studentEntityV1.getCourseId());
    studentDTO.setSemId(studentEntityV1.getSemId());
    return studentDTO;
  }
}
