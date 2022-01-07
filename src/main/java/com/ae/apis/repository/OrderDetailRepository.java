package com.ae.apis.repository;

import com.ae.apis.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
