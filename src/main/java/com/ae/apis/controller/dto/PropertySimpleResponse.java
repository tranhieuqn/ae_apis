package com.ae.apis.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class PropertySimpleResponse {
  private Long id;
  private Long productId;
  private String name;
  private String thumbnail;
}