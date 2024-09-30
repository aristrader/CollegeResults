package org.collegeWorks.collegeresults.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends Exception {

  private String errorDetails;
  private CollegeServiceErrorCodes errorCode;

  @Getter
  public enum CollegeServiceErrorCodes {
    // Validations related 1xxx
    MISSING_REQUIRED_PARAMETER(1000, HttpStatus.BAD_REQUEST, "Missing required parameter"),
    INVALID_VALUES_FOR_REQUIRED_PARAMETER(1001, HttpStatus.BAD_REQUEST,
        "Invalid values sent for the requested parameter"),

    // Service related error codes 2xxx
    INTERNAL_SERVER_ERROR(2000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    ENROLLMENT_FAILURE_STUDENT_AND_SUBJECT_FROM_DIFFERENT_COLLEGE(2000,
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Student cannot enroll in the requested subject because they both are from "),

    // Database related errors 30xx
    DATA_PERSISTENCE_ERROR(3000, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save data in DB"),
    REQUESTED_DETAILS_NOT_FOUND(3001, HttpStatus.NOT_FOUND,
        "Requested details were not found in the DB"),
    COLLEGE_DETAILS_NOT_FOUND(3002, HttpStatus.NOT_FOUND,
        "College details were not found in the DB"),
    STUDENT_DETAILS_NOT_FOUND(3003, HttpStatus.NOT_FOUND,
        "Student details were not found in the DB"),
    TEACHER_DETAILS_NOT_FOUND(3004, HttpStatus.NOT_FOUND,
        "Teacher details were not found in the DB"),
    COURSE_DETAILS_NOT_FOUND(3005, HttpStatus.NOT_FOUND, "Course details were not found in the DB"),
    SUBJECT_DETAILS_NOT_FOUND(3006, HttpStatus.NOT_FOUND,
        "Subject details were not found in the DB"),
    ENROLLMENT_DETAILS_NOT_FOUND(3007, HttpStatus.NOT_FOUND,
        "Enrollment details were not found in the DB"),

    // Database constraints violations errors 31xx
    DUPLICATE_DATA(3101, HttpStatus.BAD_REQUEST, "The data already exists for the passed keys"),
    FOREIGN_KEY_CONSTRAINT_VIOLATION(3102, HttpStatus.BAD_REQUEST,
        "Foreign key constraint violation occurred.");
    private final int errorCode;
    private final HttpStatus httpStatusCode;
    private final String errorTitle;

    CollegeServiceErrorCodes(int errCode, HttpStatus httpCode, String errMsg) {
      httpStatusCode = httpCode;
      errorCode = errCode;
      errorTitle = errMsg;
    }
  }
}
