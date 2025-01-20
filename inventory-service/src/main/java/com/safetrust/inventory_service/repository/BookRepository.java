package com.safetrust.inventory_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.safetrust.inventory_service.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    @Query("select b from book b where b.inventory.id = ?1")
    // @Query("select b from book b where b.inventory.id = ?1")
    List<Book> findAllBooksByInventoryId(long inventoryId);
}
