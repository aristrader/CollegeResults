package org.collegeWorks.collegeresults.v2.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "course_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"college_id", "course_name", "sem"})})
@Data
public class CourseDetailsEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_details_id")
  private Integer id;

  @Column(name = "college_id", nullable = false)
  private Integer collegeId;

  @Column(name = "course_name", nullable = false)
  private String courseName;

  @Column(name = "sem", nullable = false)
  private Integer semester;

  @Column(name = "class_teacher_id")
  private Integer classTeacherId;
}
