package com.ae.apis.repository;

import com.ae.apis.entity.OrderDetail;
import com.ae.apis.repository.custom.CustomOrderDetailRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, CustomOrderDetailRepository {
}
