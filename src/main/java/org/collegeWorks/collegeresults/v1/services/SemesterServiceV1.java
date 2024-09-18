package org.collegeWorks.collegeresults.v1.services;

import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.SemesterEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.SemesterRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.SemesterRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SemesterServiceV1 {

  @Autowired
  SemesterRepositoryV1 semesterRepository;

  public int insertSemester(SemesterRequestV1 semesterRequest) throws ServiceException {
    try {
      SemesterEntityV1 semesterEntityV1 = getSemesterEntityFromSemesterRequest(semesterRequest);
      return semesterRepository.save(semesterEntityV1).getId();
    } catch (DataIntegrityViolationException ex) {
      log.error("[SemesterServiceV1] Data integrity violation occurred : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    }
  }

  public SemesterEntityV1 getSemesterWithSemNo(int semNo) throws ServiceException {
    return semesterRepository.findByNumber(semNo)
        .orElseThrow(() -> new ServiceException("No such semester is present in the DB."));
  }

  private SemesterEntityV1 getSemesterEntityFromSemesterRequest(SemesterRequestV1 semesterRequest) {
    SemesterEntityV1 semesterEntityV1 = new SemesterEntityV1();
    semesterEntityV1.setNumber(semesterRequest.getSemNo());
    return semesterEntityV1;
  }
}
