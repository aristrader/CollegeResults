package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.helper.InputConstraints;
import org.collegeWorks.collegeresults.v2.dto.SubjectDetailsDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.SubjectDetailsEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.SubjectDetailsRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.SubjectDetailsRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubjectDetailsServiceV2 {

  @Autowired
  private SubjectDetailsRepositoryV2 subjectDetailsRepository;

  public Integer addSubjectDetails(SubjectDetailsRequestV2 request) throws ServiceException {
    if (!InputConstraints.isValidSubjectType(request.getSubjectType())) {
      throw new ServiceException("[SubjectDetailsServiceV2] Invalid subject type provided.");
    }
    try {
      SubjectDetailsEntityV2 entity = convertRequestToEntity(request);
      return subjectDetailsRepository.save(entity).getId();
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to add subject details: " + e.getMessage());
    }
  }

  public List<SubjectDetailsDTOV2> getAllSubjectsByCourse(int courseDetailsId)
      throws ServiceException {
    try {
      List<SubjectDetailsEntityV2> entities = subjectDetailsRepository.findByCourseDetailsId(
          courseDetailsId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[SubjectDetailsServiceV2] No subjects found for course ID: " + courseDetailsId);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by course: " + e.getMessage());
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
                + " and type: " + subjectType);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by type: " + e.getMessage());
    }
  }

  public List<SubjectDetailsDTOV2> getAllSubjectsByCourseTypeAndOptions(int courseDetailsId,
      String subjectType, String optionsName) throws ServiceException {
    try {
      List<SubjectDetailsEntityV2> entities = subjectDetailsRepository.findByCourseDetailsIdAndSubjectTypeAndOptionsName(
          courseDetailsId, subjectType, optionsName);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[SubjectDetailsServiceV2] No subjects found for course ID: " + courseDetailsId
                + ", type: " + subjectType + ", and options: " + optionsName);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by course, type, and options: "
              + e.getMessage());
    }
  }

  public List<SubjectDetailsDTOV2> getAllSubjectsByTeacherId(int teacherId)
      throws ServiceException {
    try {
      List<SubjectDetailsEntityV2> entities = subjectDetailsRepository.findBySubjectTeacherId(
          teacherId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[SubjectDetailsServiceV2] No subjects found for teacher ID: " + teacherId);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[SubjectDetailsServiceV2] Failed to fetch subjects by teacher ID: " + e.getMessage());
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
