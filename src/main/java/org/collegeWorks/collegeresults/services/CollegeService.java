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

  public int saveCollege(CollegeRequest collegeRequest) throws ServiceException {
    try {
      CollegeEntity collegeEntity = getCollegeEntityFromCollegeRequest(collegeRequest);
      return collegeRepository.save(collegeEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      log.error("[CollegeService] Data integrity violation occured : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    }
  }

  public CollegeEntity getCollegeWithNameAndAddress(String name, String address)
      throws ServiceException {
    return collegeRepository.findByCollegeNameAndAddress(name, address)
        .orElseThrow(() -> new ServiceException("No such college is present in the DB."));
  }

  public List<CollegeEntity> getCollegesWithName(String name) throws ServiceException {
    List<CollegeEntity> colleges = collegeRepository.findByCollegeNameContainingIgnoreCase(name);
    if (colleges.isEmpty()) {
      throw new ServiceException("No college found containing the name: " + name);
    }
    return colleges;
  }

  private CollegeEntity getCollegeEntityFromCollegeRequest(CollegeRequest collegeRequest) {
    CollegeEntity collegeEntity = new CollegeEntity();
    collegeEntity.setCollegeName(collegeRequest.getCollegeName());
    collegeEntity.setDirector(collegeRequest.getDirector());
    collegeEntity.setEmail(collegeRequest.getEmail());
    collegeEntity.setWebsite(collegeRequest.getWebsite());
    collegeEntity.setAddress(collegeRequest.getAddress());
    return collegeEntity;
  }

}
