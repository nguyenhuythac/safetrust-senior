package com.safetrust.book_service.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.safetrust.book_service.entity.Book;
import com.safetrust.book_service.exception.CanNotDeleteEntityException;
import com.safetrust.book_service.exception.EntityNotFoundException;
import com.safetrust.book_service.model.BookDTO;
import com.safetrust.book_service.status.EBookStatus;

public interface IBookService {
    /**
    * 
    * <p>Get pagination contact list</p>
    * @param page the offset that the index of first member
    * @param size the quantity of contacts
    * @return Page that is the amount of contacts
    *
    */
    Page<Book> getBookList(int page, int size);

    /**
    * 
    * <p>Get all contact list</p>
    * @return the amount of all contacts
    *
    */
    List<Book> getAllBooks();

    /**
    * 
    * <p>Create new contact</p>
    * @param contactEntity the new contact information
    * @return ContactEntity the new created contact
     * @throws EntityNotFoundException 
    *
    */
    Book createBook(Book book) throws EntityNotFoundException;

    /**
    * 
    * <p>Get contact by Id</p>
    * @param id the id that is used to find contact
    * @return ContactEntity the contact that is found
    *
    */
    Book getBookById(long id) throws EntityNotFoundException;

    /**
    * 
    * <p>Update Contact</p>
    * @param contactEntity the contact information
    * @return ContactEntity the updated contact
    *
    */
    Book updateBook(Book book) throws EntityNotFoundException;

    /**
    * 
    * <p>Delete Contact</p>
    * @param id the id that is deleted
     * @throws CanNotDeleteEntityException 
    *
    */
    void deleteBook(long id) throws CanNotDeleteEntityException;

    /**
    * 
    * <p>Find Contacts by first name or last name</p>
    * @param name the name is used to find contacts
    * @return the amount of contacts
    *
    */

    List<Book> getAllBooksByInventoryId(long inventoryId) throws EntityNotFoundException;

    void updatebookAfter(long id, int total, EBookStatus status);

    List<Book> searchBookByName(String name);

    List<Book> searchBookByAuthor(String author);

    List<Book> searchBookByGenre(String genre);

    List<Book> findBestBooksByOfPerInventory();

    List<Book> findOverdueBooksByOfPerInventory();

    Map<String, Long> findAvailableBooksByOfPerInventory();

}
