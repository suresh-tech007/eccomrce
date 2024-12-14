package com.eccomrce.eccomrce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eccomrce.eccomrce.model.Address;
import com.eccomrce.eccomrce.model.User;
 


public interface AdressRepository extends JpaRepository<Address,Long> {

    Optional<Address> findByStreetAddressAndCityAndZipCode(String streetAddress, String city, String zipCode);

    List<Address> findAllByUser(User user);

  
    
}
