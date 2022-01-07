package com.ae.apis.controller.query.base;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FilterParam {
    private List<Long> ids = new ArrayList<>();
}
