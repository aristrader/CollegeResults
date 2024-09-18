package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.SubjectOptionalDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectOptionalEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.SubjectOptionalRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.SubjectOptionalRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubjectOptionalServiceV1 {

  @Autowired
  private SubjectOptionalRepositoryV1 subjectOptionalRepository;

  public int addSubjectOptional(SubjectOptionalRequestV1 subjectOptionalRequest)
      throws ServiceException {
    try {
      SubjectOptionalEntityV1 entity = new SubjectOptionalEntityV1();
      entity.setSubjectId(subjectOptionalRequest.getSubjectId());
      entity.setOptionalId(subjectOptionalRequest.getOptionalId());
      return subjectOptionalRepository.save(entity).getId();
    } catch (DataIntegrityViolationException e) {
      throw new ServiceException(
          "Failed to add subject optional because the subject or optional id doesn't exists: "
              + e.getMessage());
    } catch (Exception e) {
      throw new ServiceException("Failed to add subject optional: " + e.getMessage());
    }
  }

  public SubjectOptionalDTOV1 getSubjectOptional(int subjectId, int optionalId)
      throws ServiceException {
    try {
      SubjectOptionalEntityV1 subjectOptionalEntityV1 = subjectOptionalRepository.findBySubjectIdAndOptionalId(
          subjectId, optionalId).orElseThrow(
          () -> new ServiceException("No record found for given subject and optional."));
      return convertEntityToDTO(subjectOptionalEntityV1);
    } catch (ServiceException ex) {
      throw ex;
    } catch (Exception e) {
      throw new ServiceException("Failed to retrieve subject optional: " + e.getMessage());
    }
  }

  public List<SubjectOptionalDTOV1> getSubjectOptionalBySubject(int subjectId)
      throws ServiceException {
    try {
      List<SubjectOptionalEntityV1> subjectOptionals = subjectOptionalRepository.findBySubjectId(
          subjectId);
      if (subjectOptionals.isEmpty()) {
        throw new ServiceException("No subject optional mapping present with the given values.");
      }
      return subjectOptionals.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ServiceException("Failed to retrieve subject_optionals: " + ex.getMessage());
    }
  }

  public List<SubjectOptionalDTOV1> getSubjectOptionalByOptional(int optionalId)
      throws ServiceException {
    try {
      List<SubjectOptionalEntityV1> subjectOptionals = subjectOptionalRepository.findByOptionalId(
          optionalId);
      if (subjectOptionals.isEmpty()) {
        throw new ServiceException("No subject optional mapping present with the given values.");
      }
      return subjectOptionals.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ServiceException("Failed to retrieve subject_optionals: " + ex.getMessage());
    }
  }

  public SubjectOptionalDTOV1 convertEntityToDTO(SubjectOptionalEntityV1 subjectOptionalEntityV1) {
    SubjectOptionalDTOV1 subjectOptionalDTO = new SubjectOptionalDTOV1();
    subjectOptionalDTO.setSubjectId(subjectOptionalEntityV1.getSubjectId());
    subjectOptionalDTO.setOptionalId(subjectOptionalEntityV1.getOptionalId());
    return subjectOptionalDTO;
  }
}

