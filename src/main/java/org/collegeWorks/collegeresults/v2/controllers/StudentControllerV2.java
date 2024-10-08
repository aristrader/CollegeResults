package org.collegeWorks.collegeresults.v2.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstantsV2.GET_BY_COURSE_DETAILS_ID_AND_ENROLLMENT_NO;
import static org.collegeWorks.collegeresults.constant.PathConstantsV2.GET_BY_COURSE_DETAILS_ID_AND_ROLL_NO;
import static org.collegeWorks.collegeresults.constant.PathConstantsV2.STUDENTS_PATH_V2;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstantsV2;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v2.dto.StudentDTOV2;
import org.collegeWorks.collegeresults.v2.model.StudentRequestV2;
import org.collegeWorks.collegeresults.v2.services.StudentServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(STUDENTS_PATH_V2)
@Slf4j
@RequiredArgsConstructor
public class StudentControllerV2 {

  private final StudentServiceV2 studentService;

  @PostMapping("/add")
  public ResponseEntity<RestResponse> addStudent(@Valid @RequestBody StudentRequestV2 request)
      throws ServiceException {
    log.info("[StudentControllerV2] Received request to add student: {}", request);
    Integer studentIdCreated = studentService.addStudent(request);
    RestResponse response = RestResponse.successResponse(
        String.format("The student has been successfully inserted with id : %d", studentIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_ID)
  public ResponseEntity<RestResponse> getStudentById(@PathVariable("id") Integer studentId)
      throws ServiceException {
    log.info("[StudentControllerV2] Received request to get student details by studentid: {}",
        studentId);
    StudentDTOV2 result = studentService.getStudentById(studentId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_COURSE_DETAILS_ID)
  public ResponseEntity<RestResponse> getAllStudents(
      @PathVariable("courseDetailsId") Integer courseDetailsId) throws ServiceException {
    log.info("[StudentControllerV2] Received request to get all students by courseDetailsId: {}",
        courseDetailsId);
    List<StudentDTOV2> result = studentService.getAllStudents(courseDetailsId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(GET_BY_COURSE_DETAILS_ID_AND_ROLL_NO)
  public ResponseEntity<RestResponse> getStudentByRollNo(
      @RequestParam("courseDetailsId") Integer courseDetailsId,
      @RequestParam("rollNo") String rollNo) throws ServiceException {
    log.info(
        "[StudentControllerV2] Received request to get student by rollNo and courseDetailsId: {}, {}",
        rollNo, courseDetailsId);
    StudentDTOV2 result = studentService.getStudentByRollNo(courseDetailsId, rollNo);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(GET_BY_COURSE_DETAILS_ID_AND_ENROLLMENT_NO)
  public ResponseEntity<RestResponse> getStudentByEnrollmentNo(
      @RequestParam("courseDetailsId") Integer courseDetailsId,
      @RequestParam("enrollmentNo") String enrollmentNo) throws ServiceException {
    log.info(
        "[StudentControllerV2] Received request to get student by enrollmentNo and courseDetailsId: {}, {}",
        enrollmentNo, courseDetailsId);
    StudentDTOV2 result = studentService.getStudentByEnrollmentNo(courseDetailsId, enrollmentNo);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  // TODO: Club the above 2 controllers into 1 single controller
}

