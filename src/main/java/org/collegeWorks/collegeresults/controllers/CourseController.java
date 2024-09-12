package org.collegeWorks.collegeresults.controllers;

import jakarta.validation.Valid;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.jpa.entity.CourseEntity;
import org.collegeWorks.collegeresults.model.CourseRequest;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.services.CourseService;
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
@RequestMapping(PathConstants.COURSES_PATH)
public class CourseController {

  @Autowired
  CourseService courseService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<RestResponse> insertCourses(@Valid @RequestBody CourseRequest courseRequest)
      throws ServiceException {
    log.info("[CourseController] Received request to insert course {}", courseRequest);
    int collegeIdCreated = courseService.insertCourse(courseRequest);
    RestResponse response = RestResponse.successResponse(
        String.format("The course has been successfully inserted with id : %d", collegeIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstants.GET_ALL)
  public ResponseEntity<RestResponse> getAllCoursesWithName(
      @NonNull @RequestParam("name") String name) throws ServiceException {
    log.info("[CourseController] Received request to get all courses with name similar to : {}",
        name);
    List<CourseEntity> courseEntities = courseService.getAllCoursesContainingName(name);
    RestResponse response = RestResponse.successResponse(courseEntities);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(PathConstants.GET)
  ResponseEntity<RestResponse> getCourseByNameAndLength(@NonNull @RequestParam("name") String name,
      @NonNull @RequestParam("length") int length) throws ServiceException {
    log.info("[CourseController] Received request to get the course with name : {} and length : {}",
        name, length);
    CourseEntity courseEntity = courseService.getCourseWithNameAndLength(name, length);
    RestResponse response = RestResponse.successResponse(courseEntity);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
