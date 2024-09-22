package org.collegeWorks.collegeresults.v2.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstantsV2.GET_SUBJECTS_BY_COURSE_AND_TYPE;
import static org.collegeWorks.collegeresults.constant.PathConstantsV2.GET_SUBJECTS_BY_COURSE_TYPE_AND_OPTIONS;
import static org.collegeWorks.collegeresults.constant.PathConstantsV2.GET_SUBJECTS_BY_TEACHER_ID;
import static org.collegeWorks.collegeresults.constant.PathConstantsV2.SUBJECT_DETAILS_PATH_V2;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstantsV2;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v2.dto.SubjectDetailsDTOV2;
import org.collegeWorks.collegeresults.v2.model.SubjectDetailsRequestV2;
import org.collegeWorks.collegeresults.v2.services.SubjectDetailsServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SUBJECT_DETAILS_PATH_V2)
@Slf4j
public class SubjectDetailsControllerV2 {

  @Autowired
  private SubjectDetailsServiceV2 subjectDetailsService;

  //TODO: Add the application level logic for restricting the subject type and options
  @PostMapping(PathConstantsV2.ADD)
  public ResponseEntity<RestResponse> addSubjectDetails(
      @Valid @RequestBody SubjectDetailsRequestV2 request) throws ServiceException {
    log.info("[SubjectDetailsControllerV2] Request to add subject details: {}", request);
    Integer subjectId = subjectDetailsService.addSubjectDetails(request);
    RestResponse response = RestResponse.successResponse(
        String.format("Subject details added with ID: %d", subjectId));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstantsV2.GET_SUBJECTS_BY_COURSE)
  public ResponseEntity<RestResponse> getAllSubjectsByCourse(
      @PathVariable("courseDetailsId") int courseDetailsId) throws ServiceException {
    log.info("[SubjectDetailsControllerV2] Fetching all subjects for course ID: {}",
        courseDetailsId);
    List<SubjectDetailsDTOV2> subjects = subjectDetailsService.getAllSubjectsByCourse(
        courseDetailsId);
    RestResponse response = RestResponse.successResponse(subjects);
    return ResponseEntity.ok(response);
  }

  @GetMapping(GET_SUBJECTS_BY_COURSE_AND_TYPE)
  public ResponseEntity<RestResponse> getSubjectsByCourseAndType(
      @PathVariable("courseDetailsId") int courseDetailsId,
      @PathVariable("subjectType") String subjectType) throws ServiceException {
    log.info("[SubjectDetailsControllerV2] Fetching subjects by course ID and type: {}, {}",
        courseDetailsId, subjectType);
    List<SubjectDetailsDTOV2> subjects = subjectDetailsService.getAllSubjectsByCourseAndType(
        courseDetailsId, subjectType);
    RestResponse response = RestResponse.successResponse(subjects);
    return ResponseEntity.ok(response);
  }

  // TODO: Change the implementation later because there is unique constraint so there will always be only 1 entry that is returned
  @GetMapping(GET_SUBJECTS_BY_COURSE_TYPE_AND_OPTIONS)
  public ResponseEntity<RestResponse> getSubjectsByCourseTypeAndOptions(
      @PathVariable("courseDetailsId") int courseDetailsId,
      @PathVariable("subjectType") String subjectType,
      @PathVariable("optionsName") String optionsName) throws ServiceException {
    log.info(
        "[SubjectDetailsControllerV2] Fetching subjects by course ID, type, and options: {}, {}, {}",
        courseDetailsId, subjectType, optionsName);
    List<SubjectDetailsDTOV2> subjects = subjectDetailsService.getAllSubjectsByCourseTypeAndOptions(
        courseDetailsId, subjectType, optionsName);
    RestResponse response = RestResponse.successResponse(subjects);
    return ResponseEntity.ok(response);
  }

  @GetMapping(GET_SUBJECTS_BY_TEACHER_ID)
  public ResponseEntity<RestResponse> getAllSubjectsByTeacherId(
      @PathVariable("teacherId") int teacherId) throws ServiceException {
    log.info("[SubjectDetailsControllerV2] Fetching all subjects for teacher ID: {}", teacherId);
    List<SubjectDetailsDTOV2> subjects = subjectDetailsService.getAllSubjectsByTeacherId(teacherId);
    RestResponse response = RestResponse.successResponse(subjects);
    return ResponseEntity.ok(response);
  }
}
