package org.collegeWorks.collegeresults.v2.dto;

import lombok.Data;

@Data
public class StudentDTOV2 {

  private Integer studentId;
  private String studentName;
  private String fatherName;
  private String motherName;
  private String rollNo;
  private String enrollmentNo;
  private Integer courseDetailsId;
  private String photo;
}
