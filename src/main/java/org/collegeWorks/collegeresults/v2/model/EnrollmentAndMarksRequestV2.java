package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentAndMarksRequestV2 {

  @NotNull
  private Integer studentId;
  @NotNull
  private Integer subjectDetailsId;
  private Integer mainMarks;
  private Integer cce;
  private Integer practicalMarks;
}
