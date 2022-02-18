package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.PropertyStatus;
import java.util.List;
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
public class PropertyResponse {
  private Long id;
  private Long productId;
  private String name;
  private String description;
  private String thumbnail;
  private PropertyStatus status;
  private Long mediaId;
  private List<MediaResponse> media;
}