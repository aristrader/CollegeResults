package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TeacherRequestV2 {

  @NotNull
  private Integer collegeId;
  @NotNull
  private String name;
  private Integer experience;
  @NotNull
  private String email;
  @NotNull
  @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
  private String phoneNumber;
  private String specialization;
  private String photo;
}
