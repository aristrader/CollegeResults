package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubjectDetailsRequestV2 {

  @NotNull(message = "Course details ID cannot be null")
  private Integer courseDetailsId;

  @NotNull(message = "Subject type cannot be null")
  @Size(max = 50, message = "Subject type cannot be more than 50 characters")
  private String subjectType;

  @NotNull(message = "Options name cannot be null")
  @Size(max = 100, message = "Options name cannot be more than 100 characters")
  private String optionsName;

  @NotNull(message = "Maximum subject credits cannot be null")
  private Integer maxCreditsSubject;

  private Integer maxCreditsPractical;

  private Integer subjectTeacherId;
}
