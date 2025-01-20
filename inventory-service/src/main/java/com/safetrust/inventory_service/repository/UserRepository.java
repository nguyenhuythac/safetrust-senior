package com.safetrust.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.inventory_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
