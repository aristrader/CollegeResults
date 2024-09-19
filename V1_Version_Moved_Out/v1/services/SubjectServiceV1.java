package org.collegeWorks.collegeresults.v1.services;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.SubjectDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectEntityV1;
import org.collegeWorks.collegeresults.v1.jpa.repository.SubjectRepositoryV1;
import org.collegeWorks.collegeresults.v1.model.SubjectRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubjectServiceV1 {

  private static final List<String> ALLOWED_SUBJECT_TYPES = Arrays.asList("Foundation_Course",
      "Major_1", "Major_2", "Minor", "Open", "VOC", "Internship/Project");
  @Autowired
  private SubjectRepositoryV1 subjectRepository;

  public int insertSubject(SubjectRequestV1 subjectRequest) throws ServiceException {
    if (!ALLOWED_SUBJECT_TYPES.contains(subjectRequest.getType())) {
      throw new ServiceException("Invalid subject type: " + subjectRequest.getType());
    }
    try {
      SubjectEntityV1 subjectEntity = new SubjectEntityV1();
      subjectEntity.setType(subjectRequest.getType());
      return subjectRepository.save(subjectEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      throw new ServiceException(
          "Failed to insert subject due to data integrity violation: " + ex.getMessage());
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert subject: " + ex.getMessage());
    }
  }

  public SubjectDTOV1 searchSubjects(String type) throws ServiceException {
    if (!ALLOWED_SUBJECT_TYPES.contains(type)) {
      throw new ServiceException("Invalid subject type: " + type);
    }
    try {
      SubjectEntityV1 subject = subjectRepository.findByType(type);
      if (subject == null) {
        throw new ServiceException("The subject is not present in the db");
      }
      return new SubjectDTOV1(subject.getId(), subject.getType());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception ex) {
      throw new ServiceException("Failed to search subjects: " + ex.getMessage());
    }
  }
}

