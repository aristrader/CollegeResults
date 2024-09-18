package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.SubjectOptionalDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectOptionalEntity;
import org.collegeWorks.collegeresults.v1.jpa.repository.SubjectOptionalRepository;
import org.collegeWorks.collegeresults.v1.model.SubjectOptionalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubjectOptionalService {

  @Autowired
  private SubjectOptionalRepository subjectOptionalRepository;

  public int addSubjectOptional(SubjectOptionalRequest subjectOptionalRequest)
      throws ServiceException {
    try {
      SubjectOptionalEntity entity = new SubjectOptionalEntity();
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

  public SubjectOptionalDTO getSubjectOptional(int subjectId, int optionalId)
      throws ServiceException {
    try {
      SubjectOptionalEntity subjectOptionalEntity = subjectOptionalRepository.findBySubjectIdAndOptionalId(
          subjectId, optionalId).orElseThrow(
          () -> new ServiceException("No record found for given subject and optional."));
      return convertEntityToDTO(subjectOptionalEntity);
    } catch (ServiceException ex) {
      throw ex;
    } catch (Exception e) {
      throw new ServiceException("Failed to retrieve subject optional: " + e.getMessage());
    }
  }

  public List<SubjectOptionalDTO> getSubjectOptionalBySubject(int subjectId)
      throws ServiceException {
    try {
      List<SubjectOptionalEntity> subjectOptionals = subjectOptionalRepository.findBySubjectId(
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

  public List<SubjectOptionalDTO> getSubjectOptionalByOptional(int optionalId)
      throws ServiceException {
    try {
      List<SubjectOptionalEntity> subjectOptionals = subjectOptionalRepository.findByOptionalId(
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

  public SubjectOptionalDTO convertEntityToDTO(SubjectOptionalEntity subjectOptionalEntity) {
    SubjectOptionalDTO subjectOptionalDTO = new SubjectOptionalDTO();
    subjectOptionalDTO.setSubjectId(subjectOptionalEntity.getSubjectId());
    subjectOptionalDTO.setOptionalId(subjectOptionalEntity.getOptionalId());
    return subjectOptionalDTO;
  }
}

