package com.safetrust.borrow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.borrow_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
