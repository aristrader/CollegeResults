package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

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

  @Column(name = "college_id", nullable = false)
  private Integer collegeId;

  @Column(name = "teacher_name", nullable = false)
  private String name;

  @Column(name = "experience")
  private Integer experience;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "specialization")
  private String specialization;

  @Column(name = "photo")
  private String photo;
}
