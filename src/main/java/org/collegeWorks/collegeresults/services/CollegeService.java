package org.collegeWorks.collegeresults.services;

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
    } catch (DataIntegrityViolationException ex){
      log.error("[CollegeService] Data integrity violation occured : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    }
  }

  private CollegeEntity getCollegeEntityFromCollegeRequest(CollegeRequest collegeRequest) {
    CollegeEntity collegeEntity = new CollegeEntity();
    collegeEntity.setCollegeName(collegeRequest.getCollegeName());
    collegeEntity.setDirector(collegeRequest.getDirector());
    collegeEntity.setEmail(collegeRequest.getEmail());
    collegeEntity.setWebsite(collegeRequest.getWebsite());
    collegeEntity.setAddress(collegeRequest.getAddress());
    return  collegeEntity;
  }

}
