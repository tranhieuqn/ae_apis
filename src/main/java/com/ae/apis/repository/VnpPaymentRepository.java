package com.ae.apis.repository;

import com.ae.apis.entity.PaymentVNP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VnpPaymentRepository extends JpaRepository<PaymentVNP, Long> {
    Optional<PaymentVNP> findByVnpTxnRef(String vnpTxnRef);
}
