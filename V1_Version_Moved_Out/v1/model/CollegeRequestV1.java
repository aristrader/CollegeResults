package org.collegeWorks.collegeresults.v1.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollegeRequestV1 {
  @NotNull
  private String name;
  private String director;
  private String email;
  private String website;
  @NotNull
  private String address;
}
