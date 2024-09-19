package org.collegeWorks.collegeresults.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollegeOffersSubjectDTOV1 {

  private Integer collegeOffersSubjectId;
  private Integer collegeId;
  private Integer courseId;
  private Integer semId;
  private Integer subjectOptionalId;
  private Integer maxCreditsSubject;
  private Integer maxCreditsPractical;
}
