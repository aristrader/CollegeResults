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
@Table(name = "college", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"college_name", "address"})})
@Data
public class CollegeEntityV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "college_id")
  private Integer id;

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

  @Column(name = "phone_number")
  private Integer phoneNumber;
}

