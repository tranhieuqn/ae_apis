//package com.ae.apis.controller;
//
//import com.ae.apis.controller.dto.PropertyRequest;
//import com.ae.apis.controller.dto.PropertyResponse;
//import com.ae.apis.controller.dto.PropertySimpleResponse;
//import com.ae.apis.service.PropertyService;
//import java.util.List;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/properties")
//public class PropertyController {
//
//  @Autowired
//  private PropertyService propertyService;
//
//  @GetMapping
//  public List<PropertySimpleResponse> getProperties(@RequestParam("filter.productId") Long productId) {
//    return propertyService.getProperties(productId);
//  }
//
//  @GetMapping("/{id}")
//  public PropertyResponse getProperty(@PathVariable Long id) {
//    return propertyService.getProperty(id);
//  }
//
//  @PostMapping
//  public void createProperty(@Valid @RequestBody PropertyRequest request) {
//    propertyService.createProperty(request);
//  }
//
//  @PutMapping("/{id}")
//  public void updateProperty(@PathVariable Long id, @Valid @RequestBody PropertyRequest request) {
//    propertyService.updateProperty(id, request);
//  }
//}
