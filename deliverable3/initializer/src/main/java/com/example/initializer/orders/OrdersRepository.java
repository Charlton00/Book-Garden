package com.example.initializer.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.google.protobuf.Timestamp;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    
    List<Orders> findByUserId(long userId);
    
    //Orders findByUserIdAndOrderTime(long userId, Timestamp orderTime);
}
