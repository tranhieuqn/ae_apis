package com.ae.apis.repository;

import com.ae.apis.entity.Property;
import com.ae.apis.repository.custom.CustomPropertyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, CustomPropertyRepository {
}
