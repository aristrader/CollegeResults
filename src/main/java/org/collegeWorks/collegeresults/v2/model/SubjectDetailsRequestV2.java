package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectDetailsRequestV2 {

  @NotNull
  private Integer courseDetailsId;

  @NotNull
  private String subjectType;

  @NotNull
  private String optionsName;

  @NotNull
  private Integer maxCreditsSubject;

  private Integer maxCreditsPractical;

  private Integer subjectTeacherId;
}
