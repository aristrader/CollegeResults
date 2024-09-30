package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequestV2 {

  @NotNull(message = "College ID cannot be null")
  private Integer collegeId;

  @NotNull(message = "Name cannot be null")
  @Size(max = 100, message = "Name cannot be more than 100 characters")
  private String name;

  private Integer experience;

  @NotNull(message = "Email cannot be null")
  @Size(max = 100, message = "Email cannot be more than 100 characters")
  @Email(message = "Invalid email format")
  private String email;

  @NotNull(message = "Phone number cannot be null")
  @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
  private String phoneNumber;

  @Size(max = 100, message = "Specialization cannot be more than 100 characters")
  private String specialization;

  private String photo;
}
