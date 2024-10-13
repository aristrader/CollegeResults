package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.exception.ServiceException.CollegeServiceErrorCodes;
import org.collegeWorks.collegeresults.helper.InputConstraints;
import org.collegeWorks.collegeresults.v2.dto.SubjectDetailsDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.SubjectDetailsEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.SubjectDetailsRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.SubjectDetailsRequestV2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubjectDetailsServiceV2 {

  @Autowired
  private SubjectDetailsRepositoryV2 subjectDetailsRepository;

  public Integer addSubjectDetails(SubjectDetailsRequestV2 request) throws ServiceException {
    if (!InputConstraints.isValidSubjectType(request.getSubjectType())) {
      throw new ServiceException("[SubjectDetailsServiceV2] Invalid subject type provided.",
          CollegeServiceErrorCodes.INVALID_VALUES_FOR_REQUIRED_PARAMETER);
    }
    try {
      SubjectDetailsEntityV2 entity = convertRequestToEntity(request);
      return subjectDetailsRepository.save(entity).getId();
    } catch (DataIntegrityViolationException e) {
      // Here you can check the cause to differentiate further if needed
      Throwable cause = e.getCause();
      if (cause instanceof ConstraintViolationException) {
        String message = cause.getMessage();
        if (message.contains("Duplicate entry")) {
          throw new ServiceException(
              "[SubjectDetailsServiceV2] Unique or Primary Key constraint violation: " + message,
              CollegeServiceErrorCodes.DUPLICATE_DATA);
        } else if (message.contains("foreign key constraint fails")) {
          throw new ServiceException(
              "[SubjectDetailsServiceV2] Foreign Key constraint violation: " + message,
              CollegeServiceErrorCodes.FOREIGN_KEY_CONSTRAINT_VIOLATION);
        } else {
          throw new ServiceException(
              "[SubjectDetailsServiceV2] Other constraint violation: " + message,
              CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
        }
      } else {
        throw new ServiceException(
            "[SubjectDetailsServiceV2] Data integrity violation: " + e.getMessage(),
            CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
      }
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to add subject details: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public void updateSubjectDetails(SubjectDetailsRequestV2 request) throws ServiceException {
    try {
      // Find the subject by courseDetailsId, subjectType, and optionsName
      SubjectDetailsEntityV2 existingSubjectDetails = subjectDetailsRepository.findByCourseDetailsIdAndSubjectTypeAndOptionsName(
              request.getCourseDetailsId(), request.getSubjectType(), request.getOptionsName())
          .orElseThrow(() -> new ServiceException(
              "[SubjectDetailsServiceV2] Subject details not found for the given course, subject type, and options",
              CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND));

      // Update the existing entity with the new values
      existingSubjectDetails.setMaxCreditsSubject(request.getMaxCreditsSubject());
      existingSubjectDetails.setMaxCreditsPractical(request.getMaxCreditsPractical());
      existingSubjectDetails.setSubjectTeacherId(request.getSubjectTeacherId());

      subjectDetailsRepository.save(existingSubjectDetails);
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to update subject details: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public void updateSubjectDetailsByID(Integer subjectDetailsId, SubjectDetailsRequestV2 request)
      throws ServiceException {
    try {
      SubjectDetailsEntityV2 existingSubjectDetails = subjectDetailsRepository.findById(
          subjectDetailsId).orElseThrow(() -> new ServiceException(
          "[SubjectDetailsServiceV2] Subject details not found for ID: " + subjectDetailsId,
          CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND));

      // Update the existing entity with the new values
      existingSubjectDetails.setMaxCreditsSubject(request.getMaxCreditsSubject());
      existingSubjectDetails.setMaxCreditsPractical(request.getMaxCreditsPractical());
      existingSubjectDetails.setSubjectTeacherId(request.getSubjectTeacherId());

      subjectDetailsRepository.save(existingSubjectDetails);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to update subject details: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public SubjectDetailsDTOV2 getSubjectById(Integer subjectId) throws ServiceException {
    try {
      SubjectDetailsEntityV2 entity = subjectDetailsRepository.findById(subjectId).orElseThrow(
          () -> new ServiceException(
              "[SubjectDetailsServiceV2] Subject details not found with given subjectId",
              CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to retrieve subjectDetails with the given id: "
              + e.getMessage(), CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<SubjectDetailsDTOV2> getAllSubjectsByCourse(int courseDetailsId)
      throws ServiceException {
    try {
      List<SubjectDetailsEntityV2> entities = subjectDetailsRepository.findByCourseDetailsId(
          courseDetailsId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[SubjectDetailsServiceV2] No subjects found for course ID: " + courseDetailsId,
            CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by course: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<SubjectDetailsDTOV2> getAllSubjectsByCourseAndType(int courseDetailsId,
      String subjectType) throws ServiceException {
    try {
      List<SubjectDetailsEntityV2> entities = subjectDetailsRepository.findByCourseDetailsIdAndSubjectType(
          courseDetailsId, subjectType);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[SubjectDetailsServiceV2] No subjects found for course ID: " + courseDetailsId
                + " and type: " + subjectType, CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by type: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public SubjectDetailsDTOV2 getAllSubjectsByCourseTypeAndOptions(int courseDetailsId,
      String subjectType, String optionsName) throws ServiceException {
    try {
      SubjectDetailsEntityV2 existingSubjectDetails = subjectDetailsRepository.findByCourseDetailsIdAndSubjectTypeAndOptionsName(
          courseDetailsId, subjectType, optionsName).orElseThrow(() -> new ServiceException(
          "[SubjectDetailsServiceV2] Subject details not found for the given course, subject type, and options",
          CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND));
      return convertEntityToDTO(existingSubjectDetails);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by course, type, and options: "
              + e.getMessage(), CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<SubjectDetailsDTOV2> getAllSubjectsByTeacherId(int teacherId)
      throws ServiceException {
    try {
      List<SubjectDetailsEntityV2> entities = subjectDetailsRepository.findBySubjectTeacherId(
          teacherId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[SubjectDetailsServiceV2] No subjects found for teacher ID: " + teacherId,
            CollegeServiceErrorCodes.SUBJECT_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by teacher ID: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  private SubjectDetailsEntityV2 convertRequestToEntity(SubjectDetailsRequestV2 request) {
    SubjectDetailsEntityV2 entity = new SubjectDetailsEntityV2();
    entity.setCourseDetailsId(request.getCourseDetailsId());
    entity.setSubjectType(request.getSubjectType());
    entity.setOptionsName(request.getOptionsName());
    entity.setMaxCreditsSubject(request.getMaxCreditsSubject());
    entity.setMaxCreditsPractical(request.getMaxCreditsPractical());
    entity.setSubjectTeacherId(request.getSubjectTeacherId());
    return entity;
  }

  private SubjectDetailsDTOV2 convertEntityToDTO(SubjectDetailsEntityV2 entity) {
    SubjectDetailsDTOV2 dto = new SubjectDetailsDTOV2();
    dto.setSubjectDetailsId(entity.getId());
    dto.setCourseDetailsId(entity.getCourseDetailsId());
    dto.setSubjectType(entity.getSubjectType());
    dto.setOptionsName(entity.getOptionsName());
    dto.setMaxCreditsSubject(entity.getMaxCreditsSubject());
    dto.setMaxCreditsPractical(entity.getMaxCreditsPractical());
    dto.setSubjectTeacherId(entity.getSubjectTeacherId());
    return dto;
  }
}
