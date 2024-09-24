package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "enrollment_and_marks")
@Data
public class EnrollmentAndMarksEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @NotNull
  @Column(name = "student_id", nullable = false)
  private Integer studentId;

  @NotNull
  @Column(name = "subject_details_id", nullable = false)
  private Integer subjectDetailsId;

  @PositiveOrZero
  @Column(name = "main_marks")
  private Integer mainMarks;

  @PositiveOrZero
  @Column(name = "cce")
  private Integer cce;

  @PositiveOrZero
  @Column(name = "practical_marks")
  private Integer practicalMarks;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
