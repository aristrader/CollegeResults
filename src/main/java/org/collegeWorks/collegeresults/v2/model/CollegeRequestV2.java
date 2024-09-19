package org.collegeWorks.collegeresults.v2.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollegeRequestV2 {

  @NotNull
  private String name;
  private String director;
  private String email;
  private String website;
  @NotNull
  private String address;
  private Integer phoneNumber;
}
