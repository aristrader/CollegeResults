package org.collegeWorks.collegeresults.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstants.SUBJECT_OPTIONAL_GET_BY_OPTIONAL_ID;
import static org.collegeWorks.collegeresults.constant.PathConstants.SUBJECT_OPTIONAL_GET_BY_SUBJECT_ID;

import jakarta.validation.Valid;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.dto.SubjectOptionalDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.model.SubjectOptionalRequest;
import org.collegeWorks.collegeresults.services.SubjectOptionalService;
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
@RequestMapping(PathConstants.SUBJECT_OPTIONAL_PATH)
@Slf4j
public class SubjectOptionalController {

  @Autowired
  private SubjectOptionalService subjectOptionalService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<Integer> addSubjectOptional(
      @Valid @RequestBody SubjectOptionalRequest subjectOptionalRequest) throws ServiceException {
    log.info("[SubjectOptionalController] Received request to insert subject-optional : {}",
        subjectOptionalRequest);
    int id = subjectOptionalService.addSubjectOptional(subjectOptionalRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @GetMapping(PathConstants.GET)
  public ResponseEntity<SubjectOptionalDTO> getSubjectOptional(
      @RequestParam("subjectId") int subjectId,
      @RequestParam("optionalId") int optionalId) throws ServiceException {
    log.info(
        "[SubjectOptionalController] Received request to get subject id : {} and optional id : {}",
        subjectId, optionalId);
    SubjectOptionalDTO subjectOptional = subjectOptionalService.getSubjectOptional(subjectId,
        optionalId);
    return ResponseEntity.status(HttpStatus.OK).body(subjectOptional);
  }

  @GetMapping(SUBJECT_OPTIONAL_GET_BY_SUBJECT_ID)
  public ResponseEntity<List<SubjectOptionalDTO>> getSubjectOptionalBySubject(
      @PathVariable int subjectId) throws ServiceException {
    log.info(
        "[SubjectOptionalController] Received request to get all subject-optional by subject id : {}",
        subjectId);
    List<SubjectOptionalDTO> result = subjectOptionalService.getSubjectOptionalBySubject(subjectId);
    return ResponseEntity.ok(result);
  }

  @GetMapping(SUBJECT_OPTIONAL_GET_BY_OPTIONAL_ID)
  public ResponseEntity<List<SubjectOptionalDTO>> getSubjectOptionalByOptional(
      @PathVariable int optionalId) throws ServiceException {
    log.info(
        "[SubjectOptionalController] Received request to get all subject-optional by optional id : {}",
        optionalId);
    List<SubjectOptionalDTO> result = subjectOptionalService.getSubjectOptionalByOptional(
        optionalId);
    return ResponseEntity.ok(result);
  }
}
