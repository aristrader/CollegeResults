package org.collegeWorks.collegeresults.v1.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectOptionalRequestV1 {

  @NotNull
  private int subjectId;
  @NotNull
  private int optionalId;
}

