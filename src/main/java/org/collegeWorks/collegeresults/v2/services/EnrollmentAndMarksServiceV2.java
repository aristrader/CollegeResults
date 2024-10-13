package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.exception.ServiceException.CollegeServiceErrorCodes;
import org.collegeWorks.collegeresults.v2.dto.EnrollmentAndMarksDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.EnrollmentAndMarksEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.StudentEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.SubjectDetailsEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.EnrollmentAndMarksRepositoryV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.StudentRepositoryV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.SubjectDetailsRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.EnrollmentAndMarksRequestV2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnrollmentAndMarksServiceV2 {

  private final EnrollmentAndMarksRepositoryV2 enrollmentAndMarksRepository;

  private final StudentRepositoryV2 studentRepository;

  private final SubjectDetailsRepositoryV2 subjectDetailsRepository;

  public Integer addEnrollmentAndMarks(EnrollmentAndMarksRequestV2 request)
      throws ServiceException {

    StudentEntityV2 studentEntity = studentRepository.findById(request.getStudentId()).orElseThrow(
        () -> new ServiceException("Student not found",
            CollegeServiceErrorCodes.STUDENT_DETAILS_NOT_FOUND));

    // Fetch the subject details entity
    SubjectDetailsEntityV2 subjectDetailsEntity = subjectDetailsRepository.findById(
        request.getSubjectDetailsId()).orElseThrow(
        () -> new ServiceException("Subject details not found",
            CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND));

    // Validate if both student and subject belong to the same college
    if (!studentEntity.getCourseDetailsId().equals(subjectDetailsEntity.getCourseDetailsId())) {
      throw new ServiceException(
          "The student and the subject details do not belong to the same college.",
          CollegeServiceErrorCodes.ENROLLMENT_FAILURE_STUDENT_AND_SUBJECT_FROM_DIFFERENT_COLLEGE);
    }

    try {
      EnrollmentAndMarksEntityV2 entity = convertRequestToEntity(request);
      return enrollmentAndMarksRepository.save(entity).getId();
    } catch (DataIntegrityViolationException e) {
      // Here you can check the cause to differentiate further if needed
      Throwable cause = e.getCause();
      if (cause instanceof ConstraintViolationException) {
        String message = cause.getMessage();
        if (message.contains("Duplicate entry")) {
          throw new ServiceException(
              "[StudentServiceV2] Unique or Primary Key constraint violation: " + message,
              CollegeServiceErrorCodes.DUPLICATE_DATA);
        } else if (message.contains("foreign key constraint fails")) {
          throw new ServiceException(
              "[StudentServiceV2] Foreign Key constraint violation: " + message,
              CollegeServiceErrorCodes.FOREIGN_KEY_CONSTRAINT_VIOLATION);
        } else {
          throw new ServiceException("[StudentServiceV2] Other constraint violation: " + message,
              CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
        }
      } else {
        throw new ServiceException("[StudentServiceV2] Data integrity violation: " + e.getMessage(),
            CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
      }
    } catch (Exception e) {
      throw new ServiceException(
          "[EnrollmentAndMarksServiceV2] Failed to add enrollment and marks: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public void updateMarks(EnrollmentAndMarksRequestV2 request) throws ServiceException {
    // Check if the enrollment and marks entry exists
    EnrollmentAndMarksEntityV2 existingEntry = enrollmentAndMarksRepository.findByStudentIdAndSubjectDetailsId(
        request.getStudentId(), request.getSubjectDetailsId()).orElseThrow(
        () -> new ServiceException("Enrollment and marks entry not found",
            CollegeServiceErrorCodes.ENROLLMENT_DETAILS_NOT_FOUND));

    // Update the entry with the provided marks
    existingEntry.setMainMarks(request.getMainMarks());
    existingEntry.setCce(request.getCce());
    existingEntry.setPracticalMarks(request.getPracticalMarks());

    enrollmentAndMarksRepository.save(existingEntry);
  }

  public EnrollmentAndMarksDTOV2 getByStudentAndSubjectId(int studentId, int subjectDetailsId)
      throws ServiceException {
    EnrollmentAndMarksEntityV2 entity = enrollmentAndMarksRepository.findByStudentIdAndSubjectDetailsId(
        studentId, subjectDetailsId).orElseThrow(
        () -> new ServiceException("Enrollment and marks entry not found",
            CollegeServiceErrorCodes.ENROLLMENT_DETAILS_NOT_FOUND));

    return convertEntityToDTO(entity);
  }

  public List<EnrollmentAndMarksDTOV2> getByStudentId(int studentId) throws ServiceException {
    try {
      List<EnrollmentAndMarksEntityV2> entities = enrollmentAndMarksRepository.findByStudentId(
          studentId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[EnrollmentAndMarksServiceV2] No enrollment and marks found for student ID: "
                + studentId, CollegeServiceErrorCodes.ENROLLMENT_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[EnrollmentAndMarksServiceV2] Failed to retrieve enrollment and marks: "
              + e.getMessage(), CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<EnrollmentAndMarksDTOV2> getBySubjectDetailsId(int subjectDetailsId)
      throws ServiceException {
    try {
      List<EnrollmentAndMarksEntityV2> entities = enrollmentAndMarksRepository.findBySubjectDetailsId(
          subjectDetailsId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[EnrollmentAndMarksServiceV2] No enrollment and marks found for subject details ID: "
                + subjectDetailsId, CollegeServiceErrorCodes.ENROLLMENT_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[EnrollmentAndMarksServiceV2] Failed to retrieve enrollment and marks: "
              + e.getMessage(), CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  private EnrollmentAndMarksEntityV2 convertRequestToEntity(EnrollmentAndMarksRequestV2 request) {
    EnrollmentAndMarksEntityV2 entity = new EnrollmentAndMarksEntityV2();
    entity.setStudentId(request.getStudentId());
    entity.setSubjectDetailsId(request.getSubjectDetailsId());
    entity.setMainMarks(request.getMainMarks());
    entity.setCce(request.getCce());
    entity.setPracticalMarks(request.getPracticalMarks());
    return entity;
  }

  private EnrollmentAndMarksDTOV2 convertEntityToDTO(EnrollmentAndMarksEntityV2 entity) {
    EnrollmentAndMarksDTOV2 dto = new EnrollmentAndMarksDTOV2();
    dto.setId(entity.getId());
    dto.setStudentId(entity.getStudentId());
    dto.setSubjectDetailsId(entity.getSubjectDetailsId());
    dto.setMainMarks(entity.getMainMarks());
    dto.setCce(entity.getCce());
    dto.setPracticalMarks(entity.getPracticalMarks());
    return dto;
  }
}
