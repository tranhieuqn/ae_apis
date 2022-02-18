package com.ae.apis.service;

import com.ae.apis.controller.dto.PropertyRequest;
import com.ae.apis.controller.dto.PropertyResponse;
import com.ae.apis.controller.dto.PropertySimpleResponse;
import com.ae.apis.entity.Property;

import java.util.List;

public interface PropertyService {

    List<PropertySimpleResponse> getProperties(Long productId);

    PropertyResponse getProperty(Long id);

    PropertyResponse createProperty(PropertyRequest request);

    Property getPropertyById(Long id);

    PropertyResponse updateProperty(Long id, PropertyRequest request);
}
