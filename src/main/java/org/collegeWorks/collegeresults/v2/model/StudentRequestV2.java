package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRequestV2 {

  @NotNull(message = "Student name cannot be null")
  @Size(max = 100, message = "Student name cannot be more than 100 characters")
  private String studentName;

  @Size(max = 100, message = "Father's name cannot be more than 100 characters")
  private String fatherName;

  @Size(max = 100, message = "Mother's name cannot be more than 100 characters")
  private String motherName;

  @NotNull(message = "Roll number cannot be null")
  @Size(max = 100, message = "Roll number cannot be more than 100 characters")
  private String rollNo;

  @NotNull(message = "Enrollment number cannot be null")
  @Size(max = 100, message = "Enrollment number cannot be more than 100 characters")
  private String enrollmentNo;

  @NotNull(message = "Course details ID cannot be null")
  private Integer courseDetailsId;

  private String photo;
}
