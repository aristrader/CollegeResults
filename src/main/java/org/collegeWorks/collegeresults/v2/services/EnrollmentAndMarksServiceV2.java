package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v2.dto.EnrollmentAndMarksDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.EnrollmentAndMarksEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.StudentEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.SubjectDetailsEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.EnrollmentAndMarksRepositoryV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.StudentRepositoryV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.SubjectDetailsRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.EnrollmentAndMarksRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EnrollmentAndMarksServiceV2 {

  @Autowired
  private EnrollmentAndMarksRepositoryV2 enrollmentAndMarksRepository;
  @Autowired
  private StudentRepositoryV2 studentRepository;

  @Autowired
  private SubjectDetailsRepositoryV2 subjectDetailsRepository;

  public Integer addEnrollmentAndMarks(EnrollmentAndMarksRequestV2 request)
      throws ServiceException {

    StudentEntityV2 studentEntity = studentRepository.findById(request.getStudentId())
        .orElseThrow(() -> new ServiceException("Student not found"));

    // Fetch the subject details entity
    SubjectDetailsEntityV2 subjectDetailsEntity = subjectDetailsRepository.findById(
            request.getSubjectDetailsId())
        .orElseThrow(() -> new ServiceException("Subject details not found"));

    // Validate if both student and subject belong to the same college
    if (!studentEntity.getCourseDetailsId().equals(subjectDetailsEntity.getCourseDetailsId())) {
      throw new ServiceException(
          "The student and the subject details do not belong to the same college.");
    }

    try {
      EnrollmentAndMarksEntityV2 entity = convertRequestToEntity(request);
      return enrollmentAndMarksRepository.save(entity).getId();
    } catch (Exception e) {
      throw new ServiceException(
          "[EnrollmentAndMarksServiceV2] Failed to add enrollment and marks: " + e.getMessage());
    }
  }

  public List<EnrollmentAndMarksDTOV2> getByStudentId(int studentId) throws ServiceException {
    try {
      List<EnrollmentAndMarksEntityV2> entities = enrollmentAndMarksRepository.findByStudentId(
          studentId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[EnrollmentAndMarksServiceV2] No enrollment and marks found for student ID: "
                + studentId);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[EnrollmentAndMarksServiceV2] Failed to retrieve enrollment and marks: "
              + e.getMessage());
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
                + subjectDetailsId);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[EnrollmentAndMarksServiceV2] Failed to retrieve enrollment and marks: "
              + e.getMessage());
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
