package org.collegeWorks.collegeresults.v1.controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.v1.dto.StudentDTOV1;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.model.StudentRequestForMultipleStudentsV1;
import org.collegeWorks.collegeresults.v1.model.StudentRequestSingleV1;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v1.services.StudentServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(PathConstants.STUDENTS_PATH_V1)
public class StudentControllerV1 {

  @Autowired
  StudentServiceV1 studentService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<RestResponse> insertStudent(
      @Valid @RequestBody StudentRequestSingleV1 studentRequestSingle) throws ServiceException {
    log.info("[StudentControllerV1] Received request to insert student {}", studentRequestSingle);
    int studentIdCreated = studentService.insertStudent(studentRequestSingle);
    RestResponse response = RestResponse.successResponse(
        String.format("The student has been successfully inserted with id : %d", studentIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // check that all students should be added, either return the students that you were not able to add, or not any add any student data at all.
  // test case - what will happen if the same roll no and college are repeated in the insert many students call.
  @PostMapping(PathConstants.ADD_LIST)
  public ResponseEntity<RestResponse> insertStudents(
      @Valid @RequestBody StudentRequestForMultipleStudentsV1 studentRequestForMultipleStudents)
      throws ServiceException {
    log.info("[StudentControllerV1] Received request to insert students {}",
        studentRequestForMultipleStudents);
    List<Integer> studentIdsCreated = studentService.insertStudents(
        studentRequestForMultipleStudents);
    RestResponse response = RestResponse.successResponse(
        String.format("The students have been successfully inserted with ids : %s",
            studentIdsCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstants.GET_BY_ROLL_NO_AND_COLLEGE)
  public ResponseEntity<RestResponse> getStudentByRollNo(
      @NotNull @Valid @RequestParam("collegeId") int collegeId,
      @NotNull @Valid @RequestParam("rollNo") String rollNo) throws ServiceException {
    log.info("[StudentControllerV1] Received request to get student with collegeId {} and rollNo {}",
        collegeId, rollNo);
    RestResponse response = RestResponse.successResponse(
        studentService.getStudentByCollegeIdAndRollNo(collegeId, rollNo));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(PathConstants.GET_BY_ENROLLMENT_NO_AND_COLLEGE)
  public ResponseEntity<RestResponse> getStudentByEnrollmentNo(
      @NotNull @Valid @RequestParam("collegeId") int collegeId,
      @NotNull @Valid @RequestParam("enrollmentNo") String enrollmentNo) throws ServiceException {
    log.info(
        "[StudentControllerV1] Received request to get student with collegeId {} and enrollmentNo {}",
        collegeId, enrollmentNo);
    RestResponse response = RestResponse.successResponse(
        studentService.getStudentByCollegeIdAndEnrollmentNo(collegeId, enrollmentNo));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(PathConstants.GET_ALL_STUDENTS_SPECIFIC_TO_COLLEGE_COURSE_SEM)
  public ResponseEntity<RestResponse> getStudentsByCollegeCourseAndSemester(
      @NotNull @Valid @RequestParam("collegeId") int collegeId,
      @NotNull @Valid @RequestParam("courseId") int courseId,
      @NotNull @Valid @RequestParam("semId") int semId) throws ServiceException {
    log.info(
        "[StudentControllerV1] Received request to get students for collegeId {}, courseId {}, semId {}",
        collegeId, courseId, semId);
    List<StudentDTOV1> students = studentService.getStudentsByCollegeCourseAndSemester(collegeId,
        courseId, semId);
    RestResponse response = RestResponse.successResponse(students);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}