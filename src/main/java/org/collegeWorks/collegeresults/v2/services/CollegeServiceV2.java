package org.collegeWorks.collegeresults.v2.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.exception.ServiceException.CollegeServiceErrorCodes;
import org.collegeWorks.collegeresults.v2.dto.CollegeDTOV2;
import org.collegeWorks.collegeresults.v2.jpa.entity.CollegeEntityV2;
import org.collegeWorks.collegeresults.v2.jpa.repository.CollegeRepositoryV2;
import org.collegeWorks.collegeresults.v2.model.CollegeRequestV2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollegeServiceV2 {

  @Autowired
  private CollegeRepositoryV2 collegeRepository;

  public Integer addCollege(CollegeRequestV2 request) throws ServiceException {
    try {
      CollegeEntityV2 entity = convertRequestToEntity(request);
      return collegeRepository.save(entity).getId();
    } catch (DataIntegrityViolationException e) {
      // Here you can check the cause to differentiate further if needed
      Throwable cause = e.getCause();
      if (cause instanceof ConstraintViolationException) {
        String message = cause.getMessage();
        if (message.contains("Duplicate entry")) {
          throw new ServiceException(
              "[CollegeServiceV2] Unique or Primary Key constraint violation: " + message,
              CollegeServiceErrorCodes.DUPLICATE_DATA);
        } else if (message.contains("foreign key constraint fails")) {
          throw new ServiceException(
              "[CollegeServiceV2] Foreign Key constraint violation: " + message,
              CollegeServiceErrorCodes.FOREIGN_KEY_CONSTRAINT_VIOLATION);
        } else {
          throw new ServiceException("[CollegeServiceV2] Other constraint violation: " + message,
              CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
        }
      } else {
        throw new ServiceException("[CollegeServiceV2] Data integrity violation: " + e.getMessage(),
            CollegeServiceErrorCodes.DATA_PERSISTENCE_ERROR);
      }
    } catch (Exception e) {
      throw new ServiceException("[CollegeServiceV2] Failed to add college: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public CollegeDTOV2 getCollegeById(int collegeId) throws ServiceException {
    try {
      CollegeEntityV2 entity = collegeRepository.findById(collegeId).orElseThrow(
          () -> new ServiceException("[CollegeServiceV2] College not found for ID: " + collegeId,
              CollegeServiceErrorCodes.COLLEGE_DETAILS_NOT_FOUND));
      return convertEntityToDTO(entity);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException("[CollegeServiceV2] Failed to retrieve college: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<CollegeDTOV2> getAllColleges() throws ServiceException {
    try {
      List<CollegeEntityV2> entities = collegeRepository.findAll();
      if (entities.isEmpty()) {
        throw new ServiceException("[CollegeServiceV2] No colleges found in the database.",
            CollegeServiceErrorCodes.COLLEGE_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CollegeServiceV2] Failed to retrieve colleges: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  public List<CollegeDTOV2> searchCollegesByName(String name) throws ServiceException {
    try {
      List<CollegeEntityV2> entities = collegeRepository.findByNameContainingIgnoreCase(name);
      if (entities.isEmpty()) {
        throw new ServiceException(
            "[CollegeServiceV2] No colleges found with name containing: " + name,
            CollegeServiceErrorCodes.COLLEGE_DETAILS_NOT_FOUND);
      }
      return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(
          "[CollegeServiceV2] Failed to search colleges with the given name: " + e.getMessage(),
          CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR);
    }
  }

  private CollegeDTOV2 convertEntityToDTO(CollegeEntityV2 entity) {
    CollegeDTOV2 dto = new CollegeDTOV2();
    dto.setCollegeId(entity.getId());
    dto.setCollegeName(entity.getName());
    dto.setDirector(entity.getDirector());
    dto.setEmail(entity.getEmail());
    dto.setWebsite(entity.getWebsite());
    dto.setAddress(entity.getAddress());
    dto.setPhoneNumber(entity.getPhoneNumber());
    return dto;
  }

  private CollegeEntityV2 convertRequestToEntity(CollegeRequestV2 request) {
    CollegeEntityV2 entity = new CollegeEntityV2();
    entity.setName(request.getName());
    entity.setDirector(request.getDirector());
    entity.setEmail(request.getEmail());
    entity.setWebsite(request.getWebsite());
    entity.setAddress(request.getAddress());
    entity.setPhoneNumber(request.getPhoneNumber());
    return entity;
  }
}
