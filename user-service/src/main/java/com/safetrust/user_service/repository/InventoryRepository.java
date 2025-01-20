package com.safetrust.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.user_service.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
}
