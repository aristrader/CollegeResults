package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnrollmentAndMarksRequestV2 {

  @NotNull(message = "Student ID cannot be null")
  private Integer studentId;

  @NotNull(message = "Subject Details ID cannot be null")
  private Integer subjectDetailsId;

  @Size(max = 10, message = "Main Marks cannot be more than 10 characters")
  private String mainMarks;

  @Size(max = 10, message = "Main Marks cannot be more than 10 characters")
  private String cce;

  @Size(max = 10, message = "Main Marks cannot be more than 10 characters")
  private String practicalMarks;
}
