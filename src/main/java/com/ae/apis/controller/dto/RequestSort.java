package com.ae.apis.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class RequestSort {
  private String field;
  private String direction;
}