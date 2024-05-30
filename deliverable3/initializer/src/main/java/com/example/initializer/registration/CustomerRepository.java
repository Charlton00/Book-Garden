package com.example.initializer.registration;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByUserId(long userId);
    Optional<Customer> findByEmail(String email);
    
    @Query(value = "SELECT c.* FROM customer c WHERE c.subscribed = 1 AND c.is_admin = 0 AND user_status = 'active'", nativeQuery = true)
	List<Customer> findAllSubscribedCustomers();
    
}
