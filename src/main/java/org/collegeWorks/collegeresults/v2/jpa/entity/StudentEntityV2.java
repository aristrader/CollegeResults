package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "student", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"roll_no", "course_details_id"}),
    @UniqueConstraint(columnNames = {"enrollment_no", "course_details_id"})
})
@Data
public class StudentEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
  private Integer studentId;

  @NotNull
  @Size(max = 100)
  @Column(name = "student_name", nullable = false)
  private String studentName;

  @Size(max = 100)
  @Column(name = "father_name")
  private String fatherName;

  @Size(max = 100)
  @Column(name = "mother_name")
  private String motherName;

  @NotNull
  @Column(name = "roll_no", nullable = false)
  private String rollNo;

  @NotNull
  @Column(name = "enrollment_no", nullable = false)
  private String enrollmentNo;

  @NotNull
  @Column(name = "course_details_id", nullable = false)
  private Integer courseDetailsId;

  @Column(name = "photo")
  private String photo;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
