package org.collegeWorks.collegeresults.jpa.entity;

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

@Entity(name = "College")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "College", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"College_Name", "Address"})
})
public class CollegeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "College_Name", nullable = false)
  private String collegeName;

  @Column(name = "Director")
  private String director;

  @Column(name = "Email")
  private String email;

  @Column(name = "Website")
  private String website;

  @Column(name = "Address", nullable = false)
  private String address;
}
