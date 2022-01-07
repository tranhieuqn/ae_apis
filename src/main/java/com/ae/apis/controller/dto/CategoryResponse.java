package com.ae.apis.controller.dto;

import com.ae.apis.entity.enums.CategoryStatus;
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
public class CategoryResponse {
  private Long id;
  private Long parentId;
  private String name;
  private String description;
  private String thumbnail;
  private CategoryStatus status;
  private Long mediaId;
  private List<MediaResponse> mediaDetails;
}