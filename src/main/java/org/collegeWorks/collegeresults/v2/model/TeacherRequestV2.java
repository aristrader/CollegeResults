package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequestV2 {

  @NotNull
  private Integer collegeId;
  @NotNull
  @Size(max = 100)
  private String name;
  private Integer experience;
  @NotNull
  @Size(max = 100)
  @Email
  private String email;
  @NotNull
  @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
  private String phoneNumber;
  @Size(max = 100)
  private String specialization;
  private String photo;
}
