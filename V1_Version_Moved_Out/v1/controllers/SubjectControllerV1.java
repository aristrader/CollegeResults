package org.collegeWorks.collegeresults.v1.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.v1.dto.SubjectDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.model.SubjectRequestV1;
import org.collegeWorks.collegeresults.v1.services.SubjectServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.SUBJECTS_PATH_V1)
@Slf4j
public class SubjectControllerV1 {

  @Autowired
  private SubjectServiceV1 subjectService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<Integer> addSubject(@Valid @RequestBody SubjectRequestV1 request)
      throws ServiceException {
    int subjectId = subjectService.insertSubject(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(subjectId);
  }

  @GetMapping(PathConstants.GET)
  public ResponseEntity<SubjectDTOV1> getSubjectsByType(@RequestParam("type") String type)
      throws ServiceException {
    SubjectDTOV1 subject = subjectService.searchSubjects(type);
    return ResponseEntity.ok(subject);
  }
}


