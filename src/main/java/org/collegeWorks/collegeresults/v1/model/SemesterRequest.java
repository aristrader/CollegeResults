package org.collegeWorks.collegeresults.v1.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SemesterRequest {

  @NotNull
  private int semNo;
}
