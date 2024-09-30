package org.collegeWorks.collegeresults.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.collegeWorks.collegeresults.exception.ServiceException.CollegeServiceErrorCodes;
import org.collegeWorks.collegeresults.rest.RestResponse;
import org.collegeWorks.collegeresults.rest.RestResponse.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<RestResponse> handleServiceException(ServiceException exception) {
    log.error("ServiceException caught: {}", exception.getMessage());
    RestError restError = new RestError(exception.getErrorCode().getErrorCode(),
        exception.getErrorCode().getErrorTitle(), exception.getErrorDetails());
    List<RestError> restErrorsList = new ArrayList<>();
    restErrorsList.add(restError);
    RestResponse response = RestResponse.fromErrors(restErrorsList);
    return ResponseEntity.status(exception.getErrorCode().getHttpStatusCode()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RestResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    log.error("Method argument validation failure: {}", exception.getMessage(), exception);
    // Extract field errors from the exception
    List<RestError> restErrorsList = exception.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> new RestError(
            CollegeServiceErrorCodes.MISSING_REQUIRED_PARAMETER.getErrorCode(),
            CollegeServiceErrorCodes.MISSING_REQUIRED_PARAMETER.getErrorTitle(),
            fieldError.getDefaultMessage())) // This will be the validation message e.g.,
        // "Name cannot be null"
        .collect(Collectors.toList());
    RestResponse response = RestResponse.fromErrors(restErrorsList);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestResponse> handleException(Exception exception) {
    log.error("Unknown exception caught: {}", exception.getMessage(), exception);
    RestError restError = new RestError(
        CollegeServiceErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(), exception.getMessage(),
        exception.getMessage());
    List<RestError> restErrorsList = new ArrayList<>();
    restErrorsList.add(restError);
    RestResponse response = RestResponse.fromErrors(restErrorsList);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

}
