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

@Entity(name = "semester")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "semester", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_name", "course_length"})})
public class SemesterEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sem_id")
  private int id;

  @NotNull
  @Column(name = "sem_no", nullable = false, unique = true)
  private int number;
}
