package org.collegeWorks.collegeresults.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.dto.SubjectDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.model.SubjectRequest;
import org.collegeWorks.collegeresults.services.SubjectService;
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
@RequestMapping(PathConstants.SUBJECTS_PATH)
@Slf4j
public class SubjectController {

  @Autowired
  private SubjectService subjectService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<Integer> addSubject(@Valid @RequestBody SubjectRequest request)
      throws ServiceException {
    int subjectId = subjectService.insertSubject(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(subjectId);
  }

  @GetMapping(PathConstants.GET)
  public ResponseEntity<SubjectDTO> getSubjectsByType(@RequestParam("type") String type)
      throws ServiceException {
    SubjectDTO subject = subjectService.searchSubjects(type);
    return ResponseEntity.ok(subject);
  }
}


