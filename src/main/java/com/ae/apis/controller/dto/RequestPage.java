package com.ae.apis.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class RequestPage {
  private int number;
  private int size;
}