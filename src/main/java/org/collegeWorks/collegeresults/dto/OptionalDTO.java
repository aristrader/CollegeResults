package org.collegeWorks.collegeresults.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionalDTO {

  private int id;

  private String optionalName;

  private boolean hasPractical;
}
