package org.collegeWorks.collegeresults.v2.dto;

import lombok.Data;

@Data
public class CourseDetailsDTOV2 {

  private Integer courseId;
  private Integer collegeId;
  private String courseName;
  private Integer semester;
  private Integer classTeacherId;
}
