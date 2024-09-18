package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.CollegeOffersSubjectDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.CollegeOffersSubjectEntity;
import org.collegeWorks.collegeresults.v1.jpa.repository.CollegeOffersSubjectRepository;
import org.collegeWorks.collegeresults.v1.jpa.repository.OptionalRepository;
import org.collegeWorks.collegeresults.v1.jpa.repository.SubjectOptionalRepository;
import org.collegeWorks.collegeresults.v1.model.CollegeOffersSubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollegeOffersSubjectService {

  @Autowired
  private CollegeOffersSubjectRepository collegeOffersSubjectRepository;

  @Autowired
  private SubjectOptionalRepository subjectOptionalRepository;

  @Autowired
  private OptionalRepository optionalRepository;

  public Integer addCollegeOffersSubject(CollegeOffersSubjectRequest request)
      throws ServiceException {
    try {
      boolean hasPractical = checkIfHasPractical(request.getSubjectOptionalId());
      if (hasPractical && request.getMaxCreditsPractical() == null) {
        throw new ServiceException(
            "Invalid request: maxCreditsPractical must be provided when subject option has practical.");
      }
      if (!hasPractical && request.getMaxCreditsPractical() != null) {
        throw new ServiceException(
            "Invalid request: maxCreditsPractical is provided but subject option has no practical.");
      }

      CollegeOffersSubjectEntity entity = convertRequestToEntity(request);

      return collegeOffersSubjectRepository.save(entity).getCollegeOffersSubjectId();
    } catch (DataIntegrityViolationException e) {
      throw new ServiceException(
          "Failed to add college offers subject because of data integrity issues either the college, course or sem doesn't exist: "
              + e.getMessage());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException("Failed to add college offers subject: " + e.getMessage());
    }
  }

  public List<CollegeOffersSubjectDTO> getSubjectsByCollegeCourseSem(int collegeId, int courseId,
      int semId) throws ServiceException {
    try {
      // TODO: Maybe this can be improved by having a custom query that even gives the subject name and option name in response.
      List<CollegeOffersSubjectEntity> entities = collegeOffersSubjectRepository.findByCollegeIdAndCourseIdAndSemId(
          collegeId, courseId, semId);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "No subjects found for the given college, course, and semester.");
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ServiceException("Failed to retrieve subjects: " + ex.getMessage());
    }
  }

  public CollegeOffersSubjectDTO getCreditsByCollegeCourseSemSubject(int collegeId, int courseId,
      int semId, int subjectOptionalId) throws ServiceException {
    try {
      CollegeOffersSubjectEntity entity = collegeOffersSubjectRepository.findByCollegeIdAndCourseIdAndSemIdAndSubjectOptionalId(
              collegeId, courseId, semId, subjectOptionalId)
          .orElseThrow(() -> new ServiceException("No records found for the given combination."));
      return convertEntityToDTO(entity);
    } catch (ServiceException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ServiceException("Failed to retrieve credits: " + ex.getMessage());
    }
  }

//  private boolean checkIfHasPractical(Integer subjectOptionalId) throws ServiceException {
//    // Fetch the SubjectOptionalEntity by ID
//    SubjectOptionalEntity subjectOptionalEntity = subjectOptionalRepository.findById(subjectOptionalId)
//        .orElseThrow(() -> new ServiceException("SubjectOptionalEntity not found for ID: " + subjectOptionalId));
//
//    // Fetch the OptionalEntity by ID from the found SubjectOptionalEntity
//    return optionalRepository.findById(subjectOptionalEntity.getOptionalId())
//        .map(OptionalEntity::isHasPractical)
//        .orElseThrow(() -> new ServiceException("OptionalEntity not found for ID: " + subjectOptionalEntity.getOptionalId()));
//  }

  private boolean checkIfHasPractical(Integer subjectOptionalId) throws ServiceException {
    return subjectOptionalRepository.findHasPracticalBySubjectOptionalId(subjectOptionalId)
        .orElseThrow(() -> new ServiceException(
            "SubjectOptionalEntity or OptionalEntity not found for ID: " + subjectOptionalId));
  }


  private CollegeOffersSubjectDTO convertEntityToDTO(CollegeOffersSubjectEntity entity) {
    CollegeOffersSubjectDTO dto = new CollegeOffersSubjectDTO();
    dto.setCollegeOffersSubjectId(entity.getCollegeOffersSubjectId());
    dto.setCollegeId(entity.getCollegeId());
    dto.setCourseId(entity.getCourseId());
    dto.setSemId(entity.getSemId());
    dto.setSubjectOptionalId(entity.getSubjectOptionalId());
    dto.setMaxCreditsSubject(entity.getMaxCreditsSubject());
    dto.setMaxCreditsPractical(entity.getMaxCreditsPractical());
    return dto;
  }

  private CollegeOffersSubjectEntity convertRequestToEntity(CollegeOffersSubjectRequest request) {
    CollegeOffersSubjectEntity entity = new CollegeOffersSubjectEntity();
    entity.setCollegeId(request.getCollegeId());
    entity.setCourseId(request.getCourseId());
    entity.setSemId(request.getSemId());
    entity.setSubjectOptionalId(request.getSubjectOptionalId());
    entity.setMaxCreditsSubject(request.getMaxCreditsSubject());
    entity.setMaxCreditsPractical(request.getMaxCreditsPractical());
    return entity;
  }
}
