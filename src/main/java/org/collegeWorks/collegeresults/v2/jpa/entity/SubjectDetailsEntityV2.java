package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subject_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_details_id", "subject_type", "options_name"})
})
@Data
public class SubjectDetailsEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subject_details_id")
  private Integer id;

  @Column(name = "course_details_id", nullable = false)
  private Integer courseDetailsId;

  @Column(name = "subject_type", nullable = false)
  private String subjectType;

  @Column(name = "options_name", nullable = false)
  private String optionsName;

  @Column(name = "max_credits_subject", nullable = false)
  private Integer maxCreditsSubject;

  @Column(name = "max_credits_practical")
  private Integer maxCreditsPractical;

  @Column(name = "subject_teacher_id")
  private Integer subjectTeacherId;
}
