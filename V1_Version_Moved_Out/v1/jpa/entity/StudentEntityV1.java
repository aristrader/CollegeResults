package org.collegeWorks.collegeresults.v1.jpa.entity;

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
@Table(name = "student", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"college_id", "roll_no"}),
    @UniqueConstraint(columnNames = {"college_id", "enrollment_no"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntityV1 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
  private int id;

  @Column(name = "student_name", nullable = false)
  private String name;

  @Column(name = "father_name", nullable = false)
  private String fatherName;

  @Column(name = "mother_name", nullable = false)
  private String motherName;

  @Column(name = "roll_no", nullable = false)
  private String rollNo;

  @Column(name = "enrollment_no", nullable = false)
  private String enrollmentNo;

  @Column(name = "photo")
  private String photo;

  @Column(name="college_id", nullable = false)
  private int collegeId;

  @Column(name="course_id", nullable = false)
  private int courseId;

  @Column(name="sem_id", nullable = false)
  private int semId;

  @ManyToOne
  @JoinColumn(name = "college_id", insertable = false, updatable = false)
  private CollegeEntityV1 collegeEntity;

  @ManyToOne
  @JoinColumn(name = "course_id", insertable = false, updatable = false)
  private CourseEntityV1 courseEntityV1;

  @ManyToOne
  @JoinColumn(name = "sem_id", insertable = false, updatable = false)
  private SemesterEntityV1 semesterEntityV1;

//  // Future flexibility with additional fields:
//  private String email;
//  private String phoneNumber;    // Contact information
//  private String address;        // Residential address
//  private String dateOfBirth;    // Date of birth
//  private String gender;         // Gender of the student
//  private String nationality;    // Nationality of the student
//
//  // Academic-specific data
//  private List<String> subjectsEnrolled; // List of subjects the student is enrolled in
//  private String previousSchool;         // Previous institution or school name
//  private double previousGPA;            // GPA or percentage from previous institution
//
//  // Health or Emergency Contact Info
//  private String emergencyContactName;
//  private String emergencyContactPhone;
//
//  // Other optional fields
//  private String profilePhotoUrl; // URL to student's profile photo (e.g., Google Drive link)
//  private String extraCurriculars; // Information about extracurricular activities
}