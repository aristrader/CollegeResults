package org.collegeWorks.collegeresults.v1.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "optional", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"optionals_name", "has_practical"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionalEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "optional_id")
  private int id;

  @Column(name = "optionals_name", nullable = false)
  private String optionalName;

  @Column(name = "has_practical", nullable = false)
  private boolean hasPractical;

}
