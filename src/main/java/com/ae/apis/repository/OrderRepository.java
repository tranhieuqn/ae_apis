package com.ae.apis.repository;

import com.ae.apis.entity.Order;
import com.ae.apis.repository.custom.CustomOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

    @Query(value = "" +
            " SELECT od.* FROM `order` AS od " +
            " INNER JOIN `payment` AS p on p.id = od.payment_id " +
            " INNER JOIN `payment_vnp` AS vnp on vnp.payment_id = p.id " +
            " WHERE vnp.vnp_txn_ref = :vnpTxnRef",
            nativeQuery = true
    )
    Optional<Order> findOrderByVnpTxnRef(String vnpTxnRef);
}
