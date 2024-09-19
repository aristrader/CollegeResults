package org.collegeWorks.collegeresults.v1.controllers;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.v1.jpa.entity.SemesterEntityV1;
import org.collegeWorks.collegeresults.v1.model.SemesterRequestV1;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v1.services.SemesterServiceV1;
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
@RequestMapping(PathConstants.SEMESTERS_PATH_V1)
public class SemesterControllerV1 {

  @Autowired
  SemesterServiceV1 semesterService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<RestResponse> insertSemesters(
      @Valid @RequestBody SemesterRequestV1 semesterRequest) throws ServiceException {
    log.info("[SemesterControllerV1] Received request to insert semester : {}", semesterRequest);
    int collegeIdCreated = semesterService.insertSemester(semesterRequest);
    RestResponse response = RestResponse.successResponse(
        String.format("The course has been successfully inserted with id : %d", collegeIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstants.GET)
  ResponseEntity<RestResponse> getSemesterBySemesterNumber(
      @NonNull @RequestParam("semNo") int semNo) throws ServiceException {
    log.info("[SemesterControllerV1] Received request to get the semester with number : {}", semNo);
    SemesterEntityV1 semesterEntityV1 = semesterService.getSemesterWithSemNo(semNo);
    RestResponse response = RestResponse.successResponse(semesterEntityV1);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
