package com.ae.apis.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class MediaRequest {
  private String name;
  private String description;
  private String url;
}