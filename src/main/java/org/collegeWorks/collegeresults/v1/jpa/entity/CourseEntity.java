package org.collegeWorks.collegeresults.v1.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_name", "course_length"})})
public class CourseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id")
  private int id;

  @NotNull
  @Column(name = "course_name", nullable = false)
  private String name;

  @NotNull
  @Column(name = "course_length", nullable = false)
  private int length;
}
