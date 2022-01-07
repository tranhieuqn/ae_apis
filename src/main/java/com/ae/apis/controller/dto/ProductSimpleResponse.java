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
public class ProductSimpleResponse {
  private Long id;
  private Long categoryId;
  private String name;
  private String thumbnail;
}