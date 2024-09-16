package org.collegeWorks.collegeresults.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollegeOffersSubjectDTO {

  private Integer collegeOffersSubjectId;
  private Integer collegeId;
  private Integer courseId;
  private Integer semId;
  private Integer subjectOptionalId;
  private Integer maxCreditsSubject;
  private Integer maxCreditsPractical;
}
