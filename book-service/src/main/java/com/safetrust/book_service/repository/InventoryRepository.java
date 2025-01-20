package com.safetrust.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.book_service.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
}
