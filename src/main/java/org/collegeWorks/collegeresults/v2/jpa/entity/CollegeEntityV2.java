package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "college", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"college_name", "address"})})
@Data
public class CollegeEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "college_id")
  private Integer id;

  @NotNull
  @Size(max = 100)
  @Column(name = "college_name", nullable = false)
  private String name;

  @Size(max = 100)
  @Column(name = "director")
  private String director;

  @Email
  @Size(max = 100)
  @Column(name = "email")
  private String email;

  @Size(max = 100)
  @Column(name = "website")
  private String website;

  @NotNull
  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}

