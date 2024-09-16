package org.collegeWorks.collegeresults.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "subject_optional", uniqueConstraints = @UniqueConstraint(columnNames = {"subject_id",
    "optional_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectOptionalEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subject_optional_id")
  private int id;

  @Column(name = "subject_id", nullable = false)
  private int subjectId;

  @Column(name = "optional_id", nullable = false)
  private int optionalId;

  @ManyToOne
  @JoinColumn(name = "subject_id", insertable = false, updatable = false)
  private SubjectEntity subject;

  @ManyToOne
  @JoinColumn(name = "optional_id", insertable = false, updatable = false)
  private OptionalEntity optional;
}
