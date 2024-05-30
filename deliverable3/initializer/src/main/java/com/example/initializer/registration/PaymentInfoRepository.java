package com.example.initializer.registration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

    PaymentInfo findByUserId(long userId);

    @Query(value="SELECT p.billing_address FROM payment_info p WHERE p.user_id = ?1", nativeQuery = true)
    List<Long> findAllBillingAddresses(long userId);
}
