package org.collegeWorks.collegeresults.v1.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionalRequest {

  @NotBlank(message = "Optional name is required")
  private String optionalName;

  private boolean hasPractical;
}
