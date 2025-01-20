package com.safetrust.borrow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.safetrust.borrow_service.entity.Borrow;
import com.safetrust.borrow_service.status.EBorrowStatus;

public interface BorrowRepository extends JpaRepository<Borrow, Long>{

    @Modifying
    @Query("update borrow b set b.status = ?2 where b.id = ?1")
    void updateBorrowStatusToDone(long id, EBorrowStatus status);
    
}
