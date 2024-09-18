package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.OptionalDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.OptionalEntity;
import org.collegeWorks.collegeresults.v1.jpa.repository.OptionalRepository;
import org.collegeWorks.collegeresults.v1.model.OptionalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OptionalService {

  @Autowired
  private OptionalRepository optionalRepository;

  public int insertOptional(OptionalRequest request) throws ServiceException {
    try {
      OptionalEntity optionalEntity = new OptionalEntity();
      optionalEntity.setOptionalName(request.getOptionalName());
      optionalEntity.setHasPractical(request.isHasPractical());

      // check is the above coming null if we don't send it.

      return optionalRepository.save(optionalEntity).getId();
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert optional subject: " + ex.getMessage());
    }
  }

  public List<OptionalDTO> searchOptionals(String optionalName, Boolean hasPractical)
      throws ServiceException {
    try {
      List<OptionalEntity> optionalEntities;
      if (hasPractical != null) {
        optionalEntities = optionalRepository.findByOptionalNameContainingIgnoreCaseAndHasPractical(
            optionalName, hasPractical);
      } else {
        optionalEntities = optionalRepository.findByOptionalNameContainingIgnoreCase(optionalName);
      }
      return optionalEntities.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    } catch (Exception ex) {
      throw new ServiceException("Error while searching optionals: " + ex.getMessage());
    }
  }


  private OptionalDTO convertEntityToDTO(OptionalEntity optionalEntity) {
    OptionalDTO dto = new OptionalDTO();
    dto.setId(optionalEntity.getId());
    dto.setOptionalName(optionalEntity.getOptionalName());
    dto.setHasPractical(optionalEntity.isHasPractical());
    return dto;
  }
}
