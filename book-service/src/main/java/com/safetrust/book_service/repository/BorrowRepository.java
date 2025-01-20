package com.safetrust.book_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.book_service.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long>{
    
}
