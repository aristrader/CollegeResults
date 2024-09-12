package org.collegeWorks.collegeresults.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequestSingle {

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
