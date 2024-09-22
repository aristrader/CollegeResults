package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "enrollment_and_marks")
@Data
public class EnrollmentAndMarksEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "student_id", nullable = false)
  private Integer studentId;

  @Column(name = "subject_details_id", nullable = false)
  private Integer subjectDetailsId;

  @Column(name = "main_marks")
  private Integer mainMarks;

  @Column(name = "cce")
  private Integer cce;

  @Column(name = "practical_marks")
  private Integer practicalMarks;
}
