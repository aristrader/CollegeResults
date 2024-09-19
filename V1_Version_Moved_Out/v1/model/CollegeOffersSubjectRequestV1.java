package org.collegeWorks.collegeresults.v1.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollegeOffersSubjectRequestV1 {

  @NotNull(message = "College ID cannot be null")
  private Integer collegeId;

  @NotNull(message = "Course ID cannot be null")
  private Integer courseId;

  @NotNull(message = "Semester ID cannot be null")
  private Integer semId;

  @NotNull(message = "Subject Optional ID cannot be null")
  private Integer subjectOptionalId;

  @NotNull(message = "Max Credits for Subject cannot be null")
  private Integer maxCreditsSubject;

  private Integer maxCreditsPractical;
}

