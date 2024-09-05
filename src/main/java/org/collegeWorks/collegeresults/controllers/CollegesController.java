package org.collegeWorks.collegeresults.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.model.CollegeRequest;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.services.CollegeService;
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
@RequestMapping(PathConstants.COLLEGES_PATH)
public class CollegesController {

  @Autowired
  CollegeService collegeService;

  // TODO : Have a standard response structure like RestResponse class or something
  @PostMapping(PathConstants.ADD)
  public ResponseEntity<RestResponse> insertColleges(
      @Valid @RequestBody CollegeRequest collegeRequest)
      throws ServiceException {
    log.info("[CollegesController] Received request to insert college {}", collegeRequest);
    int collegeIdCreated = collegeService.saveCollege(collegeRequest);
    RestResponse response = RestResponse.successResponse(
        String.format("The college has been successfully inserted with id : %d", collegeIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping()
  public ResponseEntity<RestResponse> getCollegeWithNameAndAddress(
      @RequestParam("name") String name, @RequestParam("address") String address)
      throws ServiceException {
    log.info(
        "[CollegesController] Received the request for finding college with name {} and address {}",
        name, address);
    RestResponse response = RestResponse.successResponse(
        collegeService.getCollegeWithNameAndAddress(name, address));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // Update already existing college details like director, etc.

  // Get list of all colleges with name that contains the specific word
  // Simply return the list

  // Get list of colleges with the exact name
  // Simply return the list
}
