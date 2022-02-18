package com.ae.apis.service.impl;

import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.controller.dto.MediaResponse;
import com.ae.apis.controller.dto.PropertyRequest;
import com.ae.apis.controller.dto.PropertyResponse;
import com.ae.apis.controller.dto.PropertySimpleResponse;
import com.ae.apis.entity.Media;
import com.ae.apis.entity.Product;
import com.ae.apis.entity.Property;
import com.ae.apis.repository.PropertyRepository;
import com.ae.apis.service.MediaService;
import com.ae.apis.service.ProductService;
import com.ae.apis.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private MediaService mediaService;

    @Override
    public List<PropertySimpleResponse> getProperties(Long productId) {
        productService.findProductById(productId);
        return repository.getProperties(productId);
    }

    @Override
    public PropertyResponse getProperty(Long id) {
        getPropertyById(id);
        return repository.getProperty(id);
    }

    @Override
    public PropertyResponse createProperty(PropertyRequest request) {
        Property property = new Property();
        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setThumbnail(request.getThumbnail());
        property.setStatus(request.getStatus());
        //set Product
        Product product = productService.findProductById(request.getProductId());
        property.setProduct(product);
        //save media
        Media media = mediaService.createMedia(request.getMedia());
        property.setMedia(media);

        repository.save(property);

        return buildPropertyResponse(property);
    }

    @Override
    public Property getPropertyById(Long id) {
        Property property = repository.findById(id).orElseThrow(
                () -> new NotFoundException(Property.class, id)
        );

        return property;
    }

    @Override
    public PropertyResponse updateProperty(Long id, PropertyRequest request) {
        Property property = getPropertyById(id);
        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setThumbnail(request.getThumbnail());
        property.setStatus(request.getStatus());
        //set Product
        if (!request.getProductId().equals(property.getProduct().getId())) {
            Product product = productService.findProductById(request.getProductId());
            property.setProduct(product);
        }
        //update media
        Media media = mediaService.updateMedia(property.getMedia(), request.getMedia());
        property.setMedia(media);

        repository.save(property);

        return buildPropertyResponse(property);
    }

    private PropertyResponse buildPropertyResponse(Property property) {
        PropertyResponse response = new PropertyResponse();
        response.setId(property.getId());
        response.setProductId(property.getProduct().getId());
        response.setName(property.getName());
        response.setDescription(property.getDescription());
        response.setThumbnail(property.getThumbnail());
        response.setStatus(property.getStatus());
        List<MediaResponse> mediaList = mediaService.getMediaDetails(property.getMedia().getId());
        response.setMedia(mediaList);

        return response;
    }
}
