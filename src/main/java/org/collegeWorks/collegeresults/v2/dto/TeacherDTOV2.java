package org.collegeWorks.collegeresults.v2.dto;

import lombok.Data;

@Data
public class TeacherDTOV2 {

  private Integer teacherId;
  private Integer collegeId;
  private String name;
  private Integer experience;
  private String email;
  private String phoneNumber;
  private String specialization;
  private String photo;
}
