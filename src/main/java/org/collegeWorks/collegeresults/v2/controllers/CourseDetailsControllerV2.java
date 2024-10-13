package org.collegeWorks.collegeresults.v2.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstantsV2.COURSES_DETAILS_PATH_V2;

import jakarta.validation.Valid;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstantsV2;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v2.dto.CourseDetailsDTOV2;
import org.collegeWorks.collegeresults.v2.model.CourseDetailsRequestV2;
import org.collegeWorks.collegeresults.v2.services.CourseDetailsServiceV2;
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
@RequestMapping(COURSES_DETAILS_PATH_V2)
@Slf4j
@RequiredArgsConstructor
public class CourseDetailsControllerV2 {

  private final CourseDetailsServiceV2 courseService;

  @PostMapping(PathConstantsV2.ADD)
  public ResponseEntity<RestResponse> addCourse(@Valid @RequestBody CourseDetailsRequestV2 request)
      throws ServiceException {
    log.info("[CourseControllerV2] Received request to add course: {}", request);
    Integer courseIdCreated = courseService.addCourse(request);
    RestResponse response = RestResponse.successResponse(
        String.format("The course has been successfully inserted with id : %d", courseIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstantsV2.GET_ALL)
  public ResponseEntity<RestResponse> getAllCourses(
      @NonNull @RequestParam("collegeId") Integer collegeId) throws ServiceException {
    log.info("[CourseControllerV2] Received request to get all courses for college ID: {}",
        collegeId);
    List<CourseDetailsDTOV2> result = courseService.getAllCoursesByCollegeId(collegeId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_COURSE_NAME)
  public ResponseEntity<RestResponse> getAllCoursesByName(
      @NonNull @RequestParam("collegeId") Integer collegeId,
      @NonNull @PathVariable("courseName") String courseName) throws ServiceException {
    log.info(
        "[CourseControllerV2] Received request to get courses for college ID: {} and course name: {}",
        collegeId, courseName);
    List<CourseDetailsDTOV2> result = courseService.getCoursesByCollegeIdAndName(collegeId,
        courseName);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET)
  public ResponseEntity<RestResponse> getCourseDetails(
      @NonNull @RequestParam("collegeId") Integer collegeId,
      @NonNull @RequestParam("courseName") String courseName,
      @NonNull @RequestParam("sem") Integer sem) throws ServiceException {
    log.info(
        "[CourseControllerV2] Received request to get course details for college ID: {}, course name: {}, semester: {}",
        collegeId, courseName, sem);
    CourseDetailsDTOV2 result = courseService.getCourseDetails(collegeId, courseName, sem);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }
}
