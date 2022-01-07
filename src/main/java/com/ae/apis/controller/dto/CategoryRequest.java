package com.ae.apis.controller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CategoryRequest {
  private Long parentId;
  private String name;
  private String description;
  private String thumbnail;
  private List<MediaRequest> mediaRequests;
}