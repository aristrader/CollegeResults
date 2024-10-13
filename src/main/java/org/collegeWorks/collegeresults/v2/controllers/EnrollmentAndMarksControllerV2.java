package org.collegeWorks.collegeresults.v2.controllers;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstantsV2;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v2.dto.EnrollmentAndMarksDTOV2;
import org.collegeWorks.collegeresults.v2.model.EnrollmentAndMarksRequestV2;
import org.collegeWorks.collegeresults.v2.services.EnrollmentAndMarksServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstantsV2.ENROLLMENT_AND_MARKS_V2)
@Slf4j
@RequiredArgsConstructor
public class EnrollmentAndMarksControllerV2 {

  private final EnrollmentAndMarksServiceV2 enrollmentAndMarksService;

  @PostMapping(PathConstantsV2.ADD)
  public ResponseEntity<RestResponse> addEnrollment(
      @Valid @RequestBody EnrollmentAndMarksRequestV2 request) throws ServiceException {
    log.info("[EnrollmentAndMarksControllerV2] Received request to add enrollment and marks: {}",
        request);
    Integer idCreated = enrollmentAndMarksService.addEnrollmentAndMarks(request);
    RestResponse response = RestResponse.successResponse(
        String.format("Enrollment and marks have been successfully inserted with id: %d",
            idCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping(PathConstantsV2.ADD_MARKS)
  public ResponseEntity<RestResponse> addMarks(
      @Valid @RequestBody EnrollmentAndMarksRequestV2 request) throws ServiceException {
    log.info("[EnrollmentAndMarksControllerV2] Received request to add marks: {}", request);

    enrollmentAndMarksService.updateMarks(request);

    RestResponse response = RestResponse.successResponse("Marks have been successfully updated.");
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_STUDENT_ID)
  public ResponseEntity<RestResponse> getByStudentId(@PathVariable("studentId") int studentId)
      throws ServiceException {
    log.info(
        "[EnrollmentAndMarksControllerV2] Received request to get enrollment and marks by student ID: {}",
        studentId);
    List<EnrollmentAndMarksDTOV2> result = enrollmentAndMarksService.getByStudentId(studentId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_SUBJECT_DETAILS_ID)
  public ResponseEntity<RestResponse> getBySubjectDetailsId(
      @PathVariable("subjectDetailsId") int subjectDetailsId) throws ServiceException {
    log.info(
        "[EnrollmentAndMarksControllerV2] Received request to get enrollment and marks by subject details ID: {}",
        subjectDetailsId);
    List<EnrollmentAndMarksDTOV2> result = enrollmentAndMarksService.getBySubjectDetailsId(
        subjectDetailsId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_STUDENT_AND_SUBJECT_ID)
  public ResponseEntity<RestResponse> getByStudentAndSubjectId(
      @PathVariable("studentId") int studentId,
      @PathVariable("subjectDetailsId") int subjectDetailsId) throws ServiceException {
    log.info(
        "[EnrollmentAndMarksControllerV2] Received request to get enrollment and marks by student ID: {} and subject details ID: {}",
        studentId, subjectDetailsId);

    EnrollmentAndMarksDTOV2 result = enrollmentAndMarksService.getByStudentAndSubjectId(studentId,
        subjectDetailsId);

    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }
}
