package com.ae.apis.controller.query;

import com.ae.apis.controller.query.base.FilterParam;
import lombok.Data;

@Data
public class CategoryFilterParam extends FilterParam {
  private Long parentId;
}