package org.collegeWorks.collegeresults.controllers;

import jakarta.validation.Valid;
import lombok.NonNull;
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

  @GetMapping(PathConstants.GET)
  public ResponseEntity<RestResponse> getCollegeWithNameAndAddress(
      @NonNull @RequestParam("name") String name, @NonNull @RequestParam("address") String address)
      throws ServiceException {
    log.info(
        "[CollegesController] Received the request for finding college with name {} and address {}",
        name, address);
    RestResponse response = RestResponse.successResponse(
        collegeService.getCollegeWithNameAndAddress(name, address));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(PathConstants.GET_ALL)
  public ResponseEntity<RestResponse> getCollegesWithName(
      @NonNull @RequestParam("name") String name)
      throws ServiceException {
    log.info(
        "[CollegesController] Received the request for finding colleges with name {}",
        name);
    RestResponse response = RestResponse.successResponse(
        collegeService.getCollegesWithName(name));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // TODO:
  // Update already existing college details like director, etc. But no update to college name and address.
  // Question while looking at the above case -> Can we just have the college name as unique??

  // Update the college name or address (very rare scenario). When thinking front end we can have a otp flow for this case.
  // Very major changes.
}
