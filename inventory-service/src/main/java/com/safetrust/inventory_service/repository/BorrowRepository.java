package com.safetrust.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.inventory_service.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long>{
    
}
