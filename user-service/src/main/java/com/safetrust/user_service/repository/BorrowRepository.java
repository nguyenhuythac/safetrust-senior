package com.safetrust.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.user_service.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long>{
    
}
