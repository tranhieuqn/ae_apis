package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.PropertyStatus;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PropertyRequest {
  private Long id;
  private Long productId;
  private String name;
  private String description;
  private String thumbnail;
  private PropertyStatus status;
  private List<MediaRequest> media;
}