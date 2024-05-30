package com.example.initializer.registration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByUserId(long userId);

    Address findById(long addressId);

    Address findByStreet(String street);

    List<Address> findAllByUserId(long userId);

}
