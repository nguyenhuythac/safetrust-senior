package com.safetrust.borrow_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetrust.borrow_service.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    // @Query("select b from book b where b.inventory.id = ?1")
    // // @Query("select b from book b where b.inventory.id = ?1")
    // List<Book> findAllBooksByInventoryId(long inventoryId);
}
