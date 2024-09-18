package org.collegeWorks.collegeresults.v1.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstants.COLLEGE_OFFERS_SUBJECT_PATH_V1;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.v1.dto.CollegeOffersSubjectDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.model.CollegeOffersSubjectRequestV1;
import org.collegeWorks.collegeresults.v1.services.CollegeOffersSubjectServiceV1;
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
@RequestMapping(COLLEGE_OFFERS_SUBJECT_PATH_V1)
@Slf4j
public class CollegeOffersSubjectControllerV1 {

  @Autowired
  private CollegeOffersSubjectServiceV1 collegeOffersSubjectService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<Integer> addCollegeOffersSubject(
      @Valid @RequestBody CollegeOffersSubjectRequestV1 request) throws ServiceException {
    log.info("[CollegeOffersSubjectControllerV1] Received request to add college offers subject: {}",
        request);
    Integer id = collegeOffersSubjectService.addCollegeOffersSubject(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @GetMapping(PathConstants.SUBJECTS)
  public ResponseEntity<List<CollegeOffersSubjectDTOV1>> getSubjectsByCollegeCourseSem(
      @RequestParam("collegeId") int collegeId, @RequestParam("courseId") int courseId,
      @RequestParam("semId") int semId) throws ServiceException {
    log.info(
        "[CollegeOffersSubjectControllerV1] Received request to get subjects by college id: {}, course id: {}, sem id: {}",
        collegeId, courseId, semId);
    List<CollegeOffersSubjectDTOV1> result = collegeOffersSubjectService.getSubjectsByCollegeCourseSem(
        collegeId, courseId, semId);
    return ResponseEntity.ok(result);
  }

  @GetMapping(PathConstants.CREDITS)
  public ResponseEntity<CollegeOffersSubjectDTOV1> getCreditsByCollegeCourseSemSubject(
      @RequestParam("collegeId") int collegeId, @RequestParam("courseId") int courseId,
      @RequestParam("semId") int semId, @RequestParam("subjectOptionalId") int subjectOptionalId)
      throws ServiceException {
    log.info(
        "[CollegeOffersSubjectControllerV1] Received request to get credits for college id: {}, course id: {}, sem id: {}, subject optional id: {}",
        collegeId, courseId, semId, subjectOptionalId);
    CollegeOffersSubjectDTOV1 result = collegeOffersSubjectService.getCreditsByCollegeCourseSemSubject(
        collegeId, courseId, semId, subjectOptionalId);
    return ResponseEntity.ok(result);
  }
}
