package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  @Column(name = "main_marks")
  @Size(max = 10)
  private String mainMarks;

  @Column(name = "cce")
  @Size(max = 10)
  private String cce;

  @Column(name = "practical_marks")
  @Size(max = 10)
  private String practicalMarks;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
