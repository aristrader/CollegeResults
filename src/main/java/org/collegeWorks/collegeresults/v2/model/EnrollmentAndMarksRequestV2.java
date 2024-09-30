package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentAndMarksRequestV2 {

  @NotNull(message = "Student ID cannot be null")
  private Integer studentId;

  @NotNull(message = "Subject Details ID cannot be null")
  private Integer subjectDetailsId;

  private Integer mainMarks;

  private Integer cce;

  private Integer practicalMarks;
}
