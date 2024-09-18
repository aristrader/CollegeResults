package org.collegeWorks.collegeresults.v1.controllers;

import jakarta.validation.Valid;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.CourseEntityV1;
import org.collegeWorks.collegeresults.v1.model.CourseRequestV1;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v1.services.CourseServiceV1;
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
@RestController()
@RequestMapping(PathConstants.COURSES_PATH_V1)
public class CourseControllerV1 {

  @Autowired
  CourseServiceV1 courseService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<RestResponse> insertCourses(@Valid @RequestBody CourseRequestV1 courseRequest)
      throws ServiceException {
    log.info("[CourseControllerV1] Received request to insert course {}", courseRequest);
    int collegeIdCreated = courseService.insertCourse(courseRequest);
    RestResponse response = RestResponse.successResponse(
        String.format("The course has been successfully inserted with id : %d", collegeIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstants.GET_ALL)
  public ResponseEntity<RestResponse> getAllCoursesWithName(
      @NonNull @RequestParam("name") String name) throws ServiceException {
    log.info("[CourseControllerV1] Received request to get all courses with name similar to : {}",
        name);
    List<CourseEntityV1> courseEntities = courseService.getAllCoursesContainingName(name);
    RestResponse response = RestResponse.successResponse(courseEntities);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(PathConstants.GET)
  ResponseEntity<RestResponse> getCourseByNameAndLength(@NonNull @RequestParam("name") String name,
      @NonNull @RequestParam("length") int length) throws ServiceException {
    log.info("[CourseControllerV1] Received request to get the course with name : {} and length : {}",
        name, length);
    CourseEntityV1 courseEntityV1 = courseService.getCourseWithNameAndLength(name, length);
    RestResponse response = RestResponse.successResponse(courseEntityV1);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
