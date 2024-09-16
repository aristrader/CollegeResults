package org.collegeWorks.collegeresults.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstants.COLLEGE_OFFERS_SUBJECT_PATH;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.dto.CollegeOffersSubjectDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.model.CollegeOffersSubjectRequest;
import org.collegeWorks.collegeresults.services.CollegeOffersSubjectService;
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
@RequestMapping(COLLEGE_OFFERS_SUBJECT_PATH)
@Slf4j
public class CollegeOffersSubjectController {

  @Autowired
  private CollegeOffersSubjectService collegeOffersSubjectService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<Integer> addCollegeOffersSubject(
      @Valid @RequestBody CollegeOffersSubjectRequest request) throws ServiceException {
    log.info("[CollegeOffersSubjectController] Received request to add college offers subject: {}",
        request);
    Integer id = collegeOffersSubjectService.addCollegeOffersSubject(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }

  @GetMapping(PathConstants.SUBJECTS)
  public ResponseEntity<List<CollegeOffersSubjectDTO>> getSubjectsByCollegeCourseSem(
      @RequestParam("collegeId") int collegeId, @RequestParam("courseId") int courseId,
      @RequestParam("semId") int semId) throws ServiceException {
    log.info(
        "[CollegeOffersSubjectController] Received request to get subjects by college id: {}, course id: {}, sem id: {}",
        collegeId, courseId, semId);
    List<CollegeOffersSubjectDTO> result = collegeOffersSubjectService.getSubjectsByCollegeCourseSem(
        collegeId, courseId, semId);
    return ResponseEntity.ok(result);
  }

  @GetMapping(PathConstants.CREDITS)
  public ResponseEntity<CollegeOffersSubjectDTO> getCreditsByCollegeCourseSemSubject(
      @RequestParam("collegeId") int collegeId, @RequestParam("courseId") int courseId,
      @RequestParam("semId") int semId, @RequestParam("subjectOptionalId") int subjectOptionalId)
      throws ServiceException {
    log.info(
        "[CollegeOffersSubjectController] Received request to get credits for college id: {}, course id: {}, sem id: {}, subject optional id: {}",
        collegeId, courseId, semId, subjectOptionalId);
    CollegeOffersSubjectDTO result = collegeOffersSubjectService.getCreditsByCollegeCourseSemSubject(
        collegeId, courseId, semId, subjectOptionalId);
    return ResponseEntity.ok(result);
  }
}
