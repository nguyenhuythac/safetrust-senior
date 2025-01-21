package com.safetrust.book_service.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safetrust.book_service.entity.Book;
import com.safetrust.book_service.repository.BookRepository;
import com.safetrust.book_service.service.IBookService;
import com.safetrust.book_service.status.EBookStatus;
import com.safetrust.book_service.exception.CanNotDeleteEntityException;
import com.safetrust.book_service.exception.EntityNotFoundException;
import com.safetrust.book_service.model.BookDTO;

@Service
@Transactional
public class BookService implements IBookService{

    private Logger logger  = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepo;

    @Override
    public Page<Book> getBookList(int page, int size) {
        PageRequest pageReq = PageRequest.of(page, size);
        Page<Book> books = bookRepo.findAll(pageReq);
        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    

    @Override
    public Book createBook(Book book) throws EntityNotFoundException {
        try {
            return bookRepo.save(book);
        } catch (DataIntegrityViolationException e) {
            long inventoryId = book.getInventory().getId();
            logger.error("Inventory is not existed with id: {}, {}", inventoryId, e);
            throw new EntityNotFoundException("Inventory is not existed with id: " + inventoryId);
        }
        
    }

    @Override
    public Book getBookById(long id) throws EntityNotFoundException {
        Optional<Book> bookEntity = bookRepo.findById(id);
        if(bookEntity.isPresent() ){
            return bookEntity.get();
        } else {
            logger.error("Book is not existed with id: {}", id);
            throw new EntityNotFoundException("Book is not existed with id: " + id);
        }
    }

    @Override
    public Book updateBook(Book book) throws EntityNotFoundException {
        long id = book.getId();
        try {
            return bookRepo.save(book);
        } catch (JpaObjectRetrievalFailureException e) {
            long inventoryId = book.getInventory().getId();
            logger.error("Inventory is not existed with id: {}, {}", inventoryId, e);
            throw new EntityNotFoundException("Inventory is not existed with id: " + inventoryId);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Book is not existed with id: {}, {}", id, e);
            throw new EntityNotFoundException("Book is not existed with id: " + id);
        }
        
    }

    @Override
    public void deleteBook(long id) throws CanNotDeleteEntityException {
        try {
            bookRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Book cannot deleted because relationship with id: {}, {}", id, e);
            throw new CanNotDeleteEntityException("Book can't be delete because relationship with id: " + id);
        }
        
    }

    @Override
    public List<Book> getAllBooksByInventoryId(long inventoryId) throws EntityNotFoundException {
        try {
            return bookRepo.findAllBooksByInventoryId(inventoryId);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("inventoryId is not existed with id: {}, {}", inventoryId, e);
            throw new EntityNotFoundException("inventoryId is not existed with id: " + inventoryId);
        }
       
    }

    @Override
    public void updatebookAfter(long id, int total, EBookStatus status) {
        bookRepo.updatebookAfterStatus(id, total, status);
    }

    @Override
    public List<Book> searchBookByName(String name) {
        return bookRepo.findByName(name);
    }

    @Override
    public List<Book> searchBookByAuthor(String author) {
        return bookRepo.findByAuthor(author);
    }

    @Override
    public List<Book> searchBookByGenre(String genre) {
        return bookRepo.findByGenre(genre);
    }

    @Override
    public List<Book> findBestBooksByOfPerInventory() {
         return bookRepo.findBestBooksByOfPerInventory();
    }

    @Override
    public List<Book> findOverdueBooksByOfPerInventory() {
        return bookRepo.findOverdueBooksByOfPerInventory(EBookStatus.OVERDUE);
    }

    @Override
    public List<Integer> findAvailableBooksByOfPerInventory() {
        return bookRepo.findAvailableBooksByOfPerInventory(EBookStatus.AVAILABLE);
    }
    
}
