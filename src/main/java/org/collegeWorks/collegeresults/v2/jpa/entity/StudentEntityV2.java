package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

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

  @Column(name = "student_name", nullable = false)
  private String studentName;

  @Column(name = "father_name")
  private String fatherName;

  @Column(name = "mother_name")
  private String motherName;

  @Column(name = "roll_no", nullable = false)
  private String rollNo;

  @Column(name = "enrollment_no", nullable = false)
  private String enrollmentNo;

  @Column(name = "course_details_id", nullable = false)
  private Integer courseDetailsId;

  @Column(name = "photo")
  private String photo;
}
