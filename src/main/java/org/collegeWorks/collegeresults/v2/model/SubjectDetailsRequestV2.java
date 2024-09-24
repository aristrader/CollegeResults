package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubjectDetailsRequestV2 {

  @NotNull
  private Integer courseDetailsId;

  @NotNull
  @Size(max = 50)
  private String subjectType;

  @NotNull
  @Size(max = 100)
  private String optionsName;

  @NotNull
  private Integer maxCreditsSubject;

  private Integer maxCreditsPractical;

  private Integer subjectTeacherId;
}
