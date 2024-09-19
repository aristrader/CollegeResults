package org.collegeWorks.collegeresults.v1.services;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.CollegeEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.CollegeRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.CollegeRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CollegeServiceV1 {

  @Autowired
  CollegeRepositoryV1 collegeRepository;

  public int insertCollege(CollegeRequestV1 collegeRequest) throws ServiceException {
    try {
      CollegeEntityV1 collegeEntity = getCollegeEntityFromCollegeRequest(collegeRequest);
      return collegeRepository.save(collegeEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      log.error("[CollegeServiceV1] Data integrity violation occurred : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert student: " + ex.getMessage());
    }
  }

  public CollegeEntityV1 getCollegeWithNameAndAddress(String name, String address)
      throws ServiceException {
    return collegeRepository.findByNameAndAddress(name, address)
        .orElseThrow(() -> new ServiceException("No such college is present in the DB."));
  }

  public List<CollegeEntityV1> getCollegesContainingName(String name) throws ServiceException {
    List<CollegeEntityV1> colleges = collegeRepository.findByNameContainingIgnoreCase(name);
    if (colleges.isEmpty()) {
      throw new ServiceException("No college found containing the name: " + name);
    }
    return colleges;
  }

  private CollegeEntityV1 getCollegeEntityFromCollegeRequest(CollegeRequestV1 collegeRequest) {
    CollegeEntityV1 collegeEntity = new CollegeEntityV1();
    collegeEntity.setName(collegeRequest.getName());
    collegeEntity.setDirector(collegeRequest.getDirector());
    collegeEntity.setEmail(collegeRequest.getEmail());
    collegeEntity.setWebsite(collegeRequest.getWebsite());
    collegeEntity.setAddress(collegeRequest.getAddress());
    return collegeEntity;
  }

}
