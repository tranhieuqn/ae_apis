package com.ae.apis.repository.custom.Impl;

import com.ae.apis.controller.dto.PropertyResponse;
import com.ae.apis.controller.dto.PropertySimpleResponse;
import com.ae.apis.repository.custom.CustomPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class CustomPropertyRepositoryImpl implements CustomPropertyRepository {
    @Autowired
    private EntityManager em;

    @Override
    public List<PropertySimpleResponse> getProperties(Long productId) {
        return buildPropertySimpleResponseQuery(em).where(qProduct.id.eq(productId)).fetch();
    }

    @Override
    public PropertyResponse getProperty(Long id) {
        return buildPropertyResponseQuery(em).where(qProperty.id.eq(id)).fetchOne();
    }
}
