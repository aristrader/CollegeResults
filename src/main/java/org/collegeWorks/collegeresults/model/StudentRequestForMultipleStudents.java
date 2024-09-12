package org.collegeWorks.collegeresults.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class StudentRequestForMultipleStudents {

  @NotNull
  private int collegeId;
  @NotNull
  private int courseId;
  @NotNull
  private int semId;

  @NotNull
  List<StudentDetails> studentDetailsList;

  @Getter
  @Setter
  public static class StudentDetails {
    @NotNull
    private String rollNo;
    @NotNull
    private String enrollmentNo;
    @NotNull
    private String name;
    private String fatherName;
    private String motherName;
  }
}