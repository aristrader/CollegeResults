package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentRequestV2 {

  @NotNull
  private String studentName;

  private String fatherName;
  private String motherName;

  @NotNull
  private String rollNo;

  @NotNull
  private String enrollmentNo;

  @NotNull
  private Integer courseDetailsId;

  private String photo;
}
