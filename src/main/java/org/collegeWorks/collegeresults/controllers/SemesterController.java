package org.collegeWorks.collegeresults.controllers;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.jpa.entity.SemesterEntity;
import org.collegeWorks.collegeresults.model.SemesterRequest;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.services.SemesterService;
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
@RequestMapping(PathConstants.SEMESTERS_PATH)
public class SemesterController {

  @Autowired
  SemesterService semesterService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<RestResponse> insertSemesters(
      @Valid @RequestBody SemesterRequest semesterRequest) throws ServiceException {
    log.info("[SemesterController] Received request to insert semester : {}", semesterRequest);
    int collegeIdCreated = semesterService.insertSemester(semesterRequest);
    RestResponse response = RestResponse.successResponse(
        String.format("The course has been successfully inserted with id : %d", collegeIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstants.GET)
  ResponseEntity<RestResponse> getSemesterBySemesterNumber(
      @NonNull @RequestParam("semNo") int semNo) throws ServiceException {
    log.info("[SemesterController] Received request to get the semester with number : {}", semNo);
    SemesterEntity semesterEntity = semesterService.getSemesterWithSemNo(semNo);
    RestResponse response = RestResponse.successResponse(semesterEntity);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
