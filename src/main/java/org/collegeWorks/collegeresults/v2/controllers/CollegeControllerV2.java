package org.collegeWorks.collegeresults.v2.controllers;

import static org.collegeWorks.collegeresults.constant.PathConstantsV2.COLLEGES_PATH_V2;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstantsV2;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.v2.dto.CollegeDTOV2;
import org.collegeWorks.collegeresults.v2.model.CollegeRequestV2;
import org.collegeWorks.collegeresults.v2.services.CollegeServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(COLLEGES_PATH_V2)
@Slf4j
public class CollegeControllerV2 {

  @Autowired
  private CollegeServiceV2 collegeService;

  @PostMapping(PathConstantsV2.ADD)
  public ResponseEntity<RestResponse> addCollege(@Valid @RequestBody CollegeRequestV2 request)
      throws ServiceException {
    log.info("[CollegeControllerV2] Received request to add college: {}", request);
    Integer collegeIdCreated = collegeService.addCollege(request);
    RestResponse response = RestResponse.successResponse(
        String.format("The college has been successfully inserted with id : %d", collegeIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_ID)
  public ResponseEntity<RestResponse> getCollegeById(@PathVariable("id") int collegeId)
      throws ServiceException {
    log.info("[CollegeControllerV2] Received request to get college by ID: {}", collegeId);
    CollegeDTOV2 result = collegeService.getCollegeById(collegeId);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_ALL)
  public ResponseEntity<RestResponse> getAllColleges() throws ServiceException {
    log.info("[CollegeControllerV2] Received request to get all colleges");
    List<CollegeDTOV2> result = collegeService.getAllColleges();
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  @GetMapping(PathConstantsV2.GET_BY_NAME)
  public ResponseEntity<RestResponse> searchCollegesByName(@PathVariable("name") String name)
      throws ServiceException {
    log.info("[CollegeControllerV2] Received request to search colleges with name containing: {}",
        name);
    List<CollegeDTOV2> result = collegeService.searchCollegesByName(name);
    RestResponse response = RestResponse.successResponse(result);
    return ResponseEntity.ok(response);
  }

  // TODO:
  // Update already existing college details like director, etc. But no update to college name and address.
  // Question while looking at the above case -> Can we just have the college name as unique??
  // Controller to get the list of colleges by name and address -> containing the name and address, ignoring case

  // Update the college name or address (very rare scenario). When thinking front end we can have a otp flow for this case.
  // Very major changes.
}

