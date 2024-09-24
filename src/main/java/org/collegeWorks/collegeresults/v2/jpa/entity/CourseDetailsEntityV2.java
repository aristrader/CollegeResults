package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "course_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"college_id", "course_name", "sem"})})
@Data
public class CourseDetailsEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_details_id")
  private Integer id;

  @NotNull
  @Column(name = "college_id", nullable = false)
  private Integer collegeId;

  @NotNull
  @Size(max = 100)
  @Column(name = "course_name", nullable = false)
  private String courseName;

  @NotNull
  @Column(name = "sem", nullable = false)
  private Integer semester;

  @Column(name = "class_teacher_id")
  private Integer classTeacherId;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
