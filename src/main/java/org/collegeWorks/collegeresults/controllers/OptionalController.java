package org.collegeWorks.collegeresults.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.constant.PathConstants;
import org.collegeWorks.collegeresults.dto.OptionalDTO;
import org.collegeWorks.collegeresults.exception.ServiceException;
import org.collegeWorks.collegeresults.model.OptionalRequest;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.services.OptionalService;
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
@RequestMapping(PathConstants.OPTIONALS_PATH)
public class OptionalController {

  @Autowired
  private OptionalService optionalService;

  @PostMapping(PathConstants.ADD)
  public ResponseEntity<RestResponse> insertOptional(
      @Valid @RequestBody OptionalRequest optionalRequest) throws ServiceException {
    log.info("[OptionalController] Received request to insert optional subject {}",
        optionalRequest);
    int optionalIdCreated = optionalService.insertOptional(optionalRequest);
    RestResponse response = RestResponse.successResponse(
        String.format("The optional has been successfully inserted with id : %d", optionalIdCreated));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(PathConstants.GET)
  public ResponseEntity<RestResponse> searchOptionals(
      @RequestParam("optionalName") @NotNull String optionalName,
      @RequestParam(value = "hasPractical", required = false) Boolean hasPractical) throws ServiceException {

    log.info("[OptionalController] Searching optionals by name '{}' and hasPractical '{}'", optionalName, hasPractical);

    List<OptionalDTO> optionalDTOList = optionalService.searchOptionals(optionalName, hasPractical);

    RestResponse response = RestResponse.successResponse(optionalDTOList);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
