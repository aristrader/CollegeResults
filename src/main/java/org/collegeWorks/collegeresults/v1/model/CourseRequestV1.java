package org.collegeWorks.collegeresults.v1.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestV1 {
  @NotNull
  private String name;
  @NotNull
  private int length;
}
