package org.collegeWorks.collegeresults.v1.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "college_offers_subject")
@Getter
@Setter
public class CollegeOffersSubjectEntityV1 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "college_offers_subject_id")
  private Integer collegeOffersSubjectId;

  @Column(name = "college_id")
  private Integer collegeId;

  @Column(name = "course_id")
  private Integer courseId;

  @Column(name = "sem_id")
  private Integer semId;

  @Column(name = "subject_optional_id")
  private Integer subjectOptionalId;

  @Column(name = "max_credits_subject")
  private Integer maxCreditsSubject;

  @Column(name = "max_credits_practical")
  private Integer maxCreditsPractical;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "college_id", insertable = false, updatable = false)
  private CollegeEntityV1 college;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "course_id", insertable = false, updatable = false)
  private CourseEntityV1 course;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sem_id", insertable = false, updatable = false)
  private SemesterEntityV1 semester;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "subject_optional_id", insertable = false, updatable = false)
  private SubjectOptionalEntityV1 subjectOptional;
}
