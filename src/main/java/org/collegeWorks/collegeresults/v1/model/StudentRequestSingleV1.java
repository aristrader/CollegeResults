package org.collegeWorks.collegeresults.v1.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequestSingleV1 {

  @NotNull
  private int collegeId;
  @NotNull
  private int courseId;
  @NotNull
  private int semId;
  @NotNull
  private String rollNo;
  @NotNull
  private String enrollmentNo;
  @NotNull
  private String name;
  private String fatherName;
  private String motherName;
}
