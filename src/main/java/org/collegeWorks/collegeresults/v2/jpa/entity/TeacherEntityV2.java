package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "teacher", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "phone_number")
})
@Data
public class TeacherEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "teacher_id")
  private Integer id;

  @NotNull
  @Column(name = "college_id", nullable = false)
  private Integer collegeId;

  @NotNull
  @Size(max = 100)
  @Column(name = "teacher_name", nullable = false)
  private String name;

  @PositiveOrZero
  @Column(name = "experience")
  private Integer experience;

  @NotNull
  @Email
  @Size(max = 100)
  @Column(name = "email", nullable = false)
  private String email;

  @NotNull
  @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Size(max = 100)
  @Column(name = "specialization")
  private String specialization;

  @Column(name = "photo")
  private String photo;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
