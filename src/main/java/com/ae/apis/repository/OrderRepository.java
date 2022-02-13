package com.ae.apis.repository;

import com.ae.apis.entity.Order;
import com.ae.apis.repository.custom.CustomOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {
}
