package org.collegeWorks.collegeresults.v2.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstantsV2.TEACHERS_PATH_V2;

import jakarta.validation.Valid;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstantsV2;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v2.dto.TeacherDTOV2;
import org.collegeWorks.collegeresults.v2.model.TeacherRequestV2;
import org.collegeWorks.collegeresults.v2.services.TeacherServiceV2;
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

@RestController
@RequestMapping(TEACHERS_PATH_V2)
@Slf4j
public class TeacherControllerV2 {

  @Autowired
  private TeacherServiceV2 teacherService;

  @PostMapping(PathConstantsV2.ADD)
  public ResponseEntity<RestResponse> addTeacher(@Valid @RequestBody TeacherRequestV2 request)
      throws ServiceException {
    log.info("[TeacherControllerV2] Received request to add teacher: {}", request);
    Integer teacherIdCreated = teacherService.addTeacher(request);
    RestResponse response = RestResponse.successResponse(
        String.format("The teacher has been successfully inserted with id : %d", teacherIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_ID)
  public ResponseEntity<RestResponse> getTeacherById(@PathVariable("id") int teacherId)
      throws ServiceException {
    log.info("[TeacherControllerV2] Received request to get teacher by ID: {}", teacherId);
    TeacherDTOV2 result = teacherService.getTeacherById(teacherId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_ALL)
  public ResponseEntity<RestResponse> getAllTeachers(
      @NonNull @RequestParam("collegeId") Integer collegeId) throws ServiceException {
    log.info("[TeacherControllerV2] Received request to get all teachers");
    List<TeacherDTOV2> result = teacherService.getAllTeachers(collegeId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_PHONE)
  public ResponseEntity<RestResponse> getTeacherByPhoneNumber(
      @PathVariable("phoneNo") String phoneNumber) throws ServiceException {
    log.info("[TeacherControllerV2] Received request to get teacher by phone number: {}",
        phoneNumber);
    TeacherDTOV2 result = teacherService.getTeacherByPhoneNumber(phoneNumber);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_EMAIL)
  public ResponseEntity<RestResponse> getTeacherByEmail(@PathVariable("email") String email)
      throws ServiceException {
    log.info("[TeacherControllerV2] Received request to get teacher by email: {}", email);
    TeacherDTOV2 result = teacherService.getTeacherByEmail(email);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }
}
