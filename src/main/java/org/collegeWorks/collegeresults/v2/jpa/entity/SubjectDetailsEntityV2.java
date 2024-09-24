package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @NotNull
  @Column(name = "course_details_id", nullable = false)
  private Integer courseDetailsId;

  @NotNull
  @Size(max = 50)
  @Column(name = "subject_type", nullable = false)
  private String subjectType;

  @NotNull
  @Size(max = 100)
  @Column(name = "options_name", nullable = false)
  private String optionsName;

  @NotNull
  @Positive
  @Column(name = "max_credits_subject", nullable = false)
  private Integer maxCreditsSubject;

  @PositiveOrZero
  @Column(name = "max_credits_practical")
  private Integer maxCreditsPractical;

  @Column(name = "subject_teacher_id")
  private Integer subjectTeacherId;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
