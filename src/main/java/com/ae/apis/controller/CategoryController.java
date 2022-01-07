package com.ae.apis.controller;

import com.ae.apis.controller.dto.CategoryRequest;
import com.ae.apis.controller.dto.CategoryResponse;
import com.ae.apis.controller.dto.CategorySimpleResponse;
import com.ae.apis.controller.query.CategoryQueryParam;
import com.ae.apis.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/paging")
  public Page<CategorySimpleResponse> getCategories(@NotNull CategoryQueryParam queryParam) {
    log.info("Get categories with param = [{}]", queryParam);
    return categoryService.getCategories(queryParam.build(null));
  }

  @GetMapping
  public List<CategorySimpleResponse> getCategories(
      @RequestParam(value = "filter.parentId", required = false) Long parentId) {
    log.info("Get categories with parentId = {}", parentId);
    return categoryService.getCategories(parentId);
  }

  @GetMapping("/{id}")
  public CategoryResponse getCategory(@PathVariable Long id) {
    log.info("Get category with id = {}", id);
    return categoryService.getCategory(id);
  }

  @PostMapping
  public void createCategory(@Valid @RequestBody CategoryRequest request) {
    log.info("Create category with request = [{}]", request);
    categoryService.createCategory(request);
  }

  @PutMapping("/{id}")
  public void updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
    log.info("Update category with id {} and request = [{}]", id, request);
    categoryService.updateCategory(id, request);
  }
}
