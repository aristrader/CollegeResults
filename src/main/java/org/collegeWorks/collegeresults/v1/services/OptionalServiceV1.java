package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.OptionalDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.OptionalEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.OptionalRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.OptionalRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OptionalServiceV1 {

  @Autowired
  private OptionalRepositoryV1 optionalRepository;

  public int insertOptional(OptionalRequestV1 request) throws ServiceException {
    try {
      OptionalEntityV1 optionalEntityV1 = new OptionalEntityV1();
      optionalEntityV1.setOptionalName(request.getOptionalName());
      optionalEntityV1.setHasPractical(request.isHasPractical());

      // check is the above coming null if we don't send it.

      return optionalRepository.save(optionalEntityV1).getId();
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert optional subject: " + ex.getMessage());
    }
  }

  public List<OptionalDTOV1> searchOptionals(String optionalName, Boolean hasPractical)
      throws ServiceException {
    try {
      List<OptionalEntityV1> optionalEntities;
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


  private OptionalDTOV1 convertEntityToDTO(OptionalEntityV1 optionalEntityV1) {
    OptionalDTOV1 dto = new OptionalDTOV1();
    dto.setId(optionalEntityV1.getId());
    dto.setOptionalName(optionalEntityV1.getOptionalName());
    dto.setHasPractical(optionalEntityV1.isHasPractical());
    return dto;
  }
}
