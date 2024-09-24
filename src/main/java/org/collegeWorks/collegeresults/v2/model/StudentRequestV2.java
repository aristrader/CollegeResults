package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRequestV2 {

  @NotNull
  @Size(max = 100)
  private String studentName;

  @Size(max = 100)
  private String fatherName;
  @Size(max = 100)
  private String motherName;

  @NotNull
  @Size(max = 100)
  private String rollNo;

  @NotNull
  @Size(max = 100)
  private String enrollmentNo;

  @NotNull
  private Integer courseDetailsId;

  private String photo;
}
