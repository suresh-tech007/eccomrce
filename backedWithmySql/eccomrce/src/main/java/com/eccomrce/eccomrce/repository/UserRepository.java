package com.eccomrce.eccomrce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eccomrce.eccomrce.model.User;


public interface UserRepository extends JpaRepository<User,Long> {

    public User  findByEmail(String email);

    public boolean existsByEmail(String email);

    // public User findByUsername(String username);
    
}
