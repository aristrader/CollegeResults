package org.collegeWorks.collegeresults.v1.services;

import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.SemesterEntity;
import org.collegeWorks.collegeresults.v1.jpa.repository.SemesterRepository;
import org.collegeWorks.collegeresults.v1.model.SemesterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SemesterService {

  @Autowired
  SemesterRepository semesterRepository;

  public int insertSemester(SemesterRequest semesterRequest) throws ServiceException {
    try {
      SemesterEntity semesterEntity = getSemesterEntityFromSemesterRequest(semesterRequest);
      return semesterRepository.save(semesterEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      log.error("[SemesterService] Data integrity violation occurred : {}", ex.getMessage(), ex);
      throw new ServiceException(ex.getMessage());
    }
  }

  public SemesterEntity getSemesterWithSemNo(int semNo) throws ServiceException {
    return semesterRepository.findByNumber(semNo)
        .orElseThrow(() -> new ServiceException("No such semester is present in the DB."));
  }

  private SemesterEntity getSemesterEntityFromSemesterRequest(SemesterRequest semesterRequest) {
    SemesterEntity semesterEntity = new SemesterEntity();
    semesterEntity.setNumber(semesterRequest.getSemNo());
    return semesterEntity;
  }
}
