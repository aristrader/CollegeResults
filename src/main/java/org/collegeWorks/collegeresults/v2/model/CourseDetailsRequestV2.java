package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseDetailsRequestV2 {
  @NotNull
  private Integer collegeId;
  @NotNull
  @Size(max = 100)
  private String courseName;
  @NotNull
  private Integer semester;
  private Integer classTeacherId;
}
