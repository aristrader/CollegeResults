package org.collegeWorks.collegeresults.v1.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstants.SUBJECT_OPTIONAL_GET_BY_OPTIONAL_ID;
import static org.collegeWorks.collegeresults.constant.PathConstants.SUBJECT_OPTIONAL_GET_BY_SUBJECT_ID;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.v1.dto.SubjectOptionalDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.model.SubjectOptionalRequestV1;
import org.collegeWorks.collegeresults.v1.services.SubjectOptionalServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(PathConstants.SUBJECT_OPTIONAL_PATH_V1)
@Slf4j
public class SubjectOptionalControllerV1 {

  @Autowired
  private SubjectOptionalServiceV1 subjectOptionalService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<Integer> addSubjectOptional(
      @Valid @RequestBody SubjectOptionalRequestV1 subjectOptionalRequest) throws ServiceException {
    log.info("[SubjectOptionalControllerV1] Received request to insert subject-optional : {}",
        subjectOptionalRequest);
    int id = subjectOptionalService.addSubjectOptional(subjectOptionalRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @GetMapping(PathConstants.GET)
  public ResponseEntity<SubjectOptionalDTOV1> getSubjectOptional(
      @RequestParam("subjectId") int subjectId, @RequestParam("optionalId") int optionalId)
      throws ServiceException {
    log.info(
        "[SubjectOptionalControllerV1] Received request to get subject id : {} and optional id : {}",
        subjectId, optionalId);
    SubjectOptionalDTOV1 subjectOptional = subjectOptionalService.getSubjectOptional(subjectId,
        optionalId);
    return ResponseEntity.status(HttpStatus.OK).body(subjectOptional);
  }

  @GetMapping(SUBJECT_OPTIONAL_GET_BY_SUBJECT_ID)
  public ResponseEntity<List<SubjectOptionalDTOV1>> getSubjectOptionalBySubject(
      @PathVariable int subjectId) throws ServiceException {
    log.info(
        "[SubjectOptionalControllerV1] Received request to get all subject-optional by subject id : {}",
        subjectId);
    List<SubjectOptionalDTOV1> result = subjectOptionalService.getSubjectOptionalBySubject(subjectId);
    return ResponseEntity.ok(result);
  }

  @GetMapping(SUBJECT_OPTIONAL_GET_BY_OPTIONAL_ID)
  public ResponseEntity<List<SubjectOptionalDTOV1>> getSubjectOptionalByOptional(
      @PathVariable int optionalId) throws ServiceException {
    log.info(
        "[SubjectOptionalControllerV1] Received request to get all subject-optional by optional id : {}",
        optionalId);
    List<SubjectOptionalDTOV1> result = subjectOptionalService.getSubjectOptionalByOptional(
        optionalId);
    return ResponseEntity.ok(result);
  }
}
