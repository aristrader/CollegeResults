package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.CollegeOffersSubjectDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.CollegeOffersSubjectEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.CollegeOffersSubjectRepositoryV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.OptionalRepositoryV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.SubjectOptionalRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.CollegeOffersSubjectRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollegeOffersSubjectServiceV1 {

  @Autowired
  private CollegeOffersSubjectRepositoryV1 collegeOffersSubjectRepository;

  @Autowired
  private SubjectOptionalRepositoryV1 subjectOptionalRepository;

  @Autowired
  private OptionalRepositoryV1 optionalRepository;

  public Integer addCollegeOffersSubject(CollegeOffersSubjectRequestV1 request)
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

      CollegeOffersSubjectEntityV1 entity = convertRequestToEntity(request);

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

  public List<CollegeOffersSubjectDTOV1> getSubjectsByCollegeCourseSem(int collegeId, int courseId,
      int semId) throws ServiceException {
    try {
      // TODO: Maybe this can be improved by having a custom query that even gives the subject name and option name in response.
      List<CollegeOffersSubjectEntityV1> entities = collegeOffersSubjectRepository.findByCollegeIdAndCourseIdAndSemId(
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

  public CollegeOffersSubjectDTOV1 getCreditsByCollegeCourseSemSubject(int collegeId, int courseId,
      int semId, int subjectOptionalId) throws ServiceException {
    try {
      CollegeOffersSubjectEntityV1 entity = collegeOffersSubjectRepository.findByCollegeIdAndCourseIdAndSemIdAndSubjectOptionalId(
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
//    // Fetch the SubjectOptionalEntityV1 by ID
//    SubjectOptionalEntityV1 subjectOptionalEntity = subjectOptionalRepository.findById(subjectOptionalId)
//        .orElseThrow(() -> new ServiceException("SubjectOptionalEntityV1 not found for ID: " + subjectOptionalId));
//
//    // Fetch the OptionalEntityV1 by ID from the found SubjectOptionalEntityV1
//    return optionalRepository.findById(subjectOptionalEntity.getOptionalId())
//        .map(OptionalEntityV1::isHasPractical)
//        .orElseThrow(() -> new ServiceException("OptionalEntityV1 not found for ID: " + subjectOptionalEntity.getOptionalId()));
//  }

  private boolean checkIfHasPractical(Integer subjectOptionalId) throws ServiceException {
    return subjectOptionalRepository.findHasPracticalBySubjectOptionalId(subjectOptionalId)
        .orElseThrow(() -> new ServiceException(
            "SubjectOptionalEntityV1 or OptionalEntityV1 not found for ID: " + subjectOptionalId));
  }


  private CollegeOffersSubjectDTOV1 convertEntityToDTO(CollegeOffersSubjectEntityV1 entity) {
    CollegeOffersSubjectDTOV1 dto = new CollegeOffersSubjectDTOV1();
    dto.setCollegeOffersSubjectId(entity.getCollegeOffersSubjectId());
    dto.setCollegeId(entity.getCollegeId());
    dto.setCourseId(entity.getCourseId());
    dto.setSemId(entity.getSemId());
    dto.setSubjectOptionalId(entity.getSubjectOptionalId());
    dto.setMaxCreditsSubject(entity.getMaxCreditsSubject());
    dto.setMaxCreditsPractical(entity.getMaxCreditsPractical());
    return dto;
  }

  private CollegeOffersSubjectEntityV1 convertRequestToEntity(CollegeOffersSubjectRequestV1 request) {
    CollegeOffersSubjectEntityV1 entity = new CollegeOffersSubjectEntityV1();
    entity.setCollegeId(request.getCollegeId());
    entity.setCourseId(request.getCourseId());
    entity.setSemId(request.getSemId());
    entity.setSubjectOptionalId(request.getSubjectOptionalId());
    entity.setMaxCreditsSubject(request.getMaxCreditsSubject());
    entity.setMaxCreditsPractical(request.getMaxCreditsPractical());
    return entity;
  }
}
