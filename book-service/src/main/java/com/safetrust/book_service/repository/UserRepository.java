package com.safetrust.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.book_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
