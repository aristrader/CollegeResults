package org.collegeWorks.collegeresults.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class StudentDTO {
  private int id;

  private String name;

  private String fatherName;

  private String motherName;

  private String rollNo;

  private String enrollmentNo;

  private String photo;

  private int collegeId;

  private int courseId;

  private int semId;
}
