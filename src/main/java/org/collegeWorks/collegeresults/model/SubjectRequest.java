package org.collegeWorks.collegeresults.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequest {

  @NotNull(message = "Type is required")
  @Size(max = 50, message = "Type cannot exceed 50 characters")
  private String type;
}
