package org.collegeWorks.collegeresults.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.rest.RestResponse.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<RestResponse> handleServiceException(ServiceException exception) {
    log.error("ServiceException caught: {}", exception.getMessage());
    RestError restError = new RestError(-1, exception.getError(), exception.getMessage());
    List<RestError> restErrorsList = new ArrayList<>();
    restErrorsList.add(restError);
    RestResponse response = RestResponse.fromErrors(restErrorsList);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(response);
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestResponse> handleException(Exception exception) {
    log.error("Unknown exception caught: {}", exception.getMessage(), exception);
    RestError restError = new RestError(-1, exception.getMessage(), exception.getMessage());
    List<RestError> restErrorsList = new ArrayList<>();
    restErrorsList.add(restError);
    RestResponse response = RestResponse.fromErrors(restErrorsList);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(response);
  }

}
