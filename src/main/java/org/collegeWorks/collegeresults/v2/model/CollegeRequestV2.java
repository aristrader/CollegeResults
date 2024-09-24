package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CollegeRequestV2 {

  @NotNull
  @Size(max = 100)
  private String name;
  @Size(max = 100)
  private String director;
  @Size(max = 100)
  private String email;
  @Size(max = 100)
  private String website;
  @NotNull
  @Size(max = 255)
  private String address;
  // TODO: Add the appropriate annotation when you change this from integer to string
  private Integer phoneNumber;
}
