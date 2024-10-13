package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CollegeRequestV2 {

  @NotNull(message = "Name cannot be null")
  @Size(max = 100, message = "Name cannot be more than 100 characters")
  private String name;

  @Size(max = 100, message = "Director name cannot be more than 100 characters")
  private String director;

  @Email(message = "Invalid email format passed")
  @Size(max = 100, message = "email cannot be more than 100 characters")
  private String email;

  @Size(max = 100, message = "website cannot be more than 100 characters")
  private String website;

  @NotNull(message = "Address cannot be null")
  @Size(max = 255, message = "address cannot be more than 255 characters")
  private String address;

  @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
  private String phoneNumber;
}
