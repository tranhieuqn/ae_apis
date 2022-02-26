package com.ae.apis.controller;

import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.EmptyResponse;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.controller.dto.CategoryRequest;
import com.ae.apis.controller.dto.CategoryResponse;
import com.ae.apis.controller.dto.CategorySimpleResponse;
import com.ae.apis.controller.query.CategoryQueryParam;
import com.ae.apis.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/paging")
    public RestResponse<?> getCategories(@NotNull CategoryQueryParam queryParam) {
        log.info("Get categories with param = [{}]", queryParam);

        return ResponseBuilder.build(categoryService.getCategories(queryParam.build()));
    }

    @GetMapping
    public RestResponse<?> getCategories(
            @RequestParam(value = "filter.parentId", required = false) Long parentId) {
        log.info("Get categories with parentId = {}", parentId);

        return ResponseBuilder.build(categoryService.getCategories(parentId));
    }

    @GetMapping("/{id}")
    public RestResponse<?> getCategory(@PathVariable Long id) {
        log.info("Get category with id = {}", id);
        return ResponseBuilder.build(categoryService.getCategory(id));
    }

    @PostMapping
    public RestResponse<?> createCategory(@Valid @RequestBody CategoryRequest request) {
        log.info("Create category with request = [{}]", request);
        categoryService.createCategory(request);

        return ResponseBuilder.build();
    }

    @PutMapping("/{id}")
    public RestResponse<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        log.info("Update category with id {} and request = [{}]", id, request);
        categoryService.updateCategory(id, request);

        return ResponseBuilder.build();
    }
}
