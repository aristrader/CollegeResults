package org.collegeWorks.collegeresults.services;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.jpa.entity.CollegeEntity;
import org.collegeWorks.collegeresults.jpa.repository.CollegeRepository;
import org.collegeWorks.collegeresults.model.CollegeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CollegeService {

  @Autowired
  CollegeRepository collegeRepository;

  public int insertCollege(CollegeRequest collegeRequest) throws ServiceException {
    try {
      CollegeEntity collegeEntity = getCollegeEntityFromCollegeRequest(collegeRequest);
      return collegeRepository.save(collegeEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      log.error("[CollegeService] Data integrity violation occurred : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    }
  }

  public CollegeEntity getCollegeWithNameAndAddress(String name, String address)
      throws ServiceException {
    return collegeRepository.findByNameAndAddress(name, address)
        .orElseThrow(() -> new ServiceException("No such college is present in the DB."));
  }

  public List<CollegeEntity> getCollegesContainingName(String name) throws ServiceException {
    List<CollegeEntity> colleges = collegeRepository.findByNameContainingIgnoreCase(name);
    if (colleges.isEmpty()) {
      throw new ServiceException("No college found containing the name: " + name);
    }
    return colleges;
  }

  private CollegeEntity getCollegeEntityFromCollegeRequest(CollegeRequest collegeRequest) {
    CollegeEntity collegeEntity = new CollegeEntity();
    collegeEntity.setName(collegeRequest.getName());
    collegeEntity.setDirector(collegeRequest.getDirector());
    collegeEntity.setEmail(collegeRequest.getEmail());
    collegeEntity.setWebsite(collegeRequest.getWebsite());
    collegeEntity.setAddress(collegeRequest.getAddress());
    return collegeEntity;
  }

}
