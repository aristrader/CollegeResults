package org.collegeWorks.collegeresults.v1.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "college")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "college", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"college_name", "address"})
})
public class CollegeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "college_id")
  private int id;

  @Column(name = "college_name", nullable = false)
  private String name;

  @Column(name = "director")
  private String director;

  @Column(name = "email")
  private String email;

  @Column(name = "website")
  private String website;

  @Column(name = "address", nullable = false)
  private String address;

  @OneToMany(mappedBy = "collegeEntity", orphanRemoval = true)  // This ensures the bidirectional mapping
  @Fetch(FetchMode.SUBSELECT)  // Avoid N+1 problem by using sub-select
  private Set<StudentEntity> students;  // A set of students associated with the college, will only be fetched when explicitly called.
}
