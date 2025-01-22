package com.safetrust.book_service.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.safetrust.book_service.entity.Book;
import com.safetrust.book_service.status.EBookStatus;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from book b where b.inventory.id = ?1")
    List<Book> findAllBooksByInventoryId(long inventoryId);

    @Modifying
    @Query("update book b set b.status = ?3, b.borrowedTotal = ?2 where b.id = ?1")
    void updatebookAfterStatus(long id, int total, EBookStatus borrowing);

    @Query("select b from book b where b.name like %?1%")
    List<Book> findByName(String name);

    @Query("select b from book b where b.author like %?1%")
    List<Book> findByAuthor(String author);

    @Query("select b from book b where b.genre like %?1%")
    List<Book> findByGenre(String genre);

    @Query("select b1 from book b1" 
    + " join (select b.inventory.id inventoryId, max(b.borrowedTotal) borrowedTotal from book b group by b.inventory.id) b2"
    + " on b1.inventory.id = b2.inventoryId and b1.borrowedTotal = b2.borrowedTotal"
    )
    List<Book> findBestBooksByOfPerInventory();

    @Query("select b from book b where b.status = ?1")
    List<Book> findOverdueBooksByOfPerInventory(EBookStatus overdue);

    @Query("select b.inventory.name, count(b.id) from book b where b.status = ?1 group by b.inventory.id")
    List<Object[]> findAvailableBooksByOfPerInventory(EBookStatus status);
}
