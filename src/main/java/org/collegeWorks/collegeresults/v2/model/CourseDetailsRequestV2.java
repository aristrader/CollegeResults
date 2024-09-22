package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseDetailsRequestV2 {
  @NotNull
  private Integer collegeId;
  @NotNull
  private String courseName;
  @NotNull
  private Integer semester;
  private Integer classTeacherId;
}
