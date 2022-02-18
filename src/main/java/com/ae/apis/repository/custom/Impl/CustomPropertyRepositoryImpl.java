package com.ae.apis.repository.custom.Impl;

import com.ae.apis.controller.dto.MediaResponse;
import com.ae.apis.controller.dto.PropertyResponse;
import com.ae.apis.controller.dto.PropertySimpleResponse;
import com.ae.apis.repository.custom.CustomPropertyRepository;
import com.ae.apis.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class CustomPropertyRepositoryImpl implements CustomPropertyRepository {
    @Autowired
    private EntityManager em;

    @Autowired
    private MediaService mediaService;

    @Override
    public List<PropertySimpleResponse> getProperties(Long productId) {
        return buildPropertySimpleResponseQuery(em).where(qProduct.id.eq(productId)).fetch();
    }

    @Override
    public PropertyResponse getProperty(Long id) {
        PropertyResponse response = buildPropertyResponseQuery(em).where(qProperty.id.eq(id)).fetchOne();
        if (response != null && response.getMediaId() != null) {
            List<MediaResponse> mediaList = mediaService.getMediaDetails(response.getMediaId());
            response.setMedia(mediaList);
        }
        return response;
    }
}
