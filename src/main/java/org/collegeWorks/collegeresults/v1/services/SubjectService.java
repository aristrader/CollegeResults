package org.collegeWorks.collegeresults.v1.services;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.v1.dto.SubjectDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.SubjectEntity;
import org.collegeWorks.collegeresults.v1.jpa.repository.SubjectRepository;
import org.collegeWorks.collegeresults.v1.model.SubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubjectService {

  private static final List<String> ALLOWED_SUBJECT_TYPES = Arrays.asList("Foundation_Course",
      "Major_1", "Major_2", "Minor", "Open", "VOC", "Internship/Project");
  @Autowired
  private SubjectRepository subjectRepository;

  public int insertSubject(SubjectRequest subjectRequest) throws ServiceException {
    if (!ALLOWED_SUBJECT_TYPES.contains(subjectRequest.getType())) {
      throw new ServiceException("Invalid subject type: " + subjectRequest.getType());
    }
    try {
      SubjectEntity subjectEntity = new SubjectEntity();
      subjectEntity.setType(subjectRequest.getType());
      return subjectRepository.save(subjectEntity).getId();
    } catch (DataIntegrityViolationException ex) {
      throw new ServiceException(
          "Failed to insert subject due to data integrity violation: " + ex.getMessage());
    } catch (Exception ex) {
      throw new ServiceException("Failed to insert subject: " + ex.getMessage());
    }
  }

  public SubjectDTO searchSubjects(String type) throws ServiceException {
    if (!ALLOWED_SUBJECT_TYPES.contains(type)) {
      throw new ServiceException("Invalid subject type: " + type);
    }
    try {
      SubjectEntity subject = subjectRepository.findByType(type);
      if (subject == null) {
        throw new ServiceException("The subject is not present in the db");
      }
      return new SubjectDTO(subject.getId(), subject.getType());
    } catch (ServiceException e) {
      throw e;
    } catch (Exception ex) {
      throw new ServiceException("Failed to search subjects: " + ex.getMessage());
    }
  }
}

