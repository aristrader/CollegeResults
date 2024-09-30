package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseDetailsRequestV2 {

  @NotNull(message = "College ID cannot be null")
  private Integer collegeId;

  @NotNull(message = "Course name cannot be null")
  @Size(max = 100, message = "Course name cannot exceed 100 characters")
  private String courseName;

  @NotNull(message = "Semester cannot be null")
  private Integer semester;

  private Integer classTeacherId;
}
