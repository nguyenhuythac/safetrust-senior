package com.safetrust.borrow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.borrow_service.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
}
