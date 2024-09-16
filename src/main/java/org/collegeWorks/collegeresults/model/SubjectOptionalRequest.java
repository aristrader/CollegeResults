package org.collegeWorks.collegeresults.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectOptionalRequest {

  @NotNull
  private int subjectId;
  @NotNull
  private int optionalId;
}

