package com.ae.apis.controller;

import com.ae.apis.controller.dto.ProductRequest;
import com.ae.apis.controller.dto.ProductResponse;
import com.ae.apis.controller.dto.ProductSimpleResponse;
import com.ae.apis.controller.query.ProductQueryParam;
import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.EmptyResponse;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.service.ProductService;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Log4j2
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/paging")
    public RestResponse<Page<ProductSimpleResponse>> getProducts(@NotNull ProductQueryParam queryParam) {
        log.info("Get products with param = [{}]", queryParam);
        return ResponseBuilder.build(productService.getProducts(queryParam.build(null)));
    }

    @GetMapping("/{id}")
    public RestResponse<ProductResponse> getProduct(@PathVariable Long id) {
        log.info("Get product with id = {}", id);
        return ResponseBuilder.build(productService.getProduct(id));
    }

    @PostMapping
    public RestResponse<Object> createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Create product with request = [{}]", request);
        productService.createProduct(request);
        return EmptyResponse.instance;
    }

    @PutMapping("/{id}")
    public RestResponse<Object> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        log.info("Update product with id {} and request = [{}]", id, request);
        productService.updateProduct(id, request);
        return EmptyResponse.instance;
    }
}
