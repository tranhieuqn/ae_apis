package com.ae.apis.controller.query;

import com.ae.apis.controller.query.base.FilterParam;
import lombok.Data;

@Data
public class ProductFilterParam extends FilterParam {
  private Long categoryId;
  private String name;
}
