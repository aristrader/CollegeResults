package org.collegeWorks.collegeresults.v2.dto;

import lombok.Data;

@Data
public class EnrollmentAndMarksDTOV2 {

  private Integer id;
  private Integer studentId;
  private Integer subjectDetailsId;
  private Integer mainMarks;
  private Integer cce;
  private Integer practicalMarks;
}
