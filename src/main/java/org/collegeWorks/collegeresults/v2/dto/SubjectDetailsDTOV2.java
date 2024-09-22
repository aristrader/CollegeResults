package org.collegeWorks.collegeresults.v2.dto;

import lombok.Data;

@Data
public class SubjectDetailsDTOV2 {
  private Integer subjectDetailsId;
  private Integer courseDetailsId;
  private String subjectType;
  private String optionsName;
  private Integer maxCreditsSubject;
  private Integer maxCreditsPractical;
  private Integer subjectTeacherId;
}
