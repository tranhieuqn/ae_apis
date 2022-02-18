package com.ae.apis.controller;

import com.ae.apis.config.error.ResponseBuilder;
import com.ae.apis.config.error.dto.EmptyResponse;
import com.ae.apis.config.error.dto.RestResponse;
import com.ae.apis.controller.dto.PropertyRequest;
import com.ae.apis.service.PropertyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/properties")
@Log4j2
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public RestResponse<?> getProperties(@RequestParam("filter.productId") Long productId) {
        log.info("Get properties with filter.productId = [{}]", productId);
        return ResponseBuilder.build(propertyService.getProperties(productId));
    }

    @GetMapping("/{id}")
    public RestResponse<?> getProperty(@PathVariable Long id) {
        log.info("Get property with id = [{}]", id);
        return ResponseBuilder.build(propertyService.getProperty(id));
    }

    @PostMapping
    public RestResponse<?> createProperty(@Valid @RequestBody PropertyRequest request) {
        log.info("Create property with request = [{}]", request);
        propertyService.createProperty(request);

        return EmptyResponse.instance;
    }

    @PutMapping("/{id}")
    public RestResponse<?> updateProperty(@PathVariable Long id, @Valid @RequestBody PropertyRequest request) {
        log.info("Update property with id {} and request = [{}]", id, request);
        propertyService.updateProperty(id, request);

        return EmptyResponse.instance;
    }
}
