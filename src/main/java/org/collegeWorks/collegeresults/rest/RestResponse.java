package org.collegeWorks.collegeresults.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({ "metaData", "data", "errors" })
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class RestResponse
{
  private RestMetaDataTo metaData;
  private Object data;
  private List<RestError> errors;

  public static RestResponse successResponse(Object data)
  {
    RestResponse r = new RestResponse();
    r.setData(data);
    return r;
  }

  public static RestResponse fromErrors(List<RestError> errors) {
    RestResponse response = new RestResponse();
    response.errors = errors;
    return response;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonInclude(Include.NON_NULL)
  @JsonPropertyOrder({ "value", "metaInfo" })
  public static class RestMetaDataTo
  {
    private Map<String, Object> value;
    private String metaInfo;
  }

  @JsonPropertyOrder({ "code", "title", "detail" })
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class RestError
  {
    private int code;
    private String title;
    private String detail;
  }
}
