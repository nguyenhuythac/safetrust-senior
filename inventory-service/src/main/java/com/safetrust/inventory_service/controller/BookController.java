package com.safetrust.inventory_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetrust.inventory_service.entity.Book;
import com.safetrust.inventory_service.exception.CanNotDeleteEntityException;
import com.safetrust.inventory_service.exception.EntityNotFoundException;
import com.safetrust.inventory_service.exception.UnmatchIDException;
import com.safetrust.inventory_service.mapper.BookMapper;
import com.safetrust.inventory_service.model.BookDTO;
import com.safetrust.inventory_service.service.IBookService;

import jakarta.validation.Valid;

/**
 * 
 * book controller Rest api class.
 * 
 * @author Thac Nguyen
 */
@RestController
@RequestMapping("/book")
public class BookController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IBookService bookService;

    @Autowired
    BookMapper bookMapper;

    /**
     * 
     * <p>
     * Get pagination books Restful api
     * </p>
     * 
     * @param page the offset that the index of first member
     * @param size the quantity of books
     * @return List<book> amount of books
     *
     */
    @GetMapping("pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> getbooks(@PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        logger.info("Get list book with offset: {}, pageSize: {} ", offset, pageSize);
        Page<Book> books = bookService.getBookList(offset, pageSize);
        logger.info("list book object query: {} ", bookService.toString());
        return new ResponseEntity<>(
                books.stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Search books by name Restful api
     * </p>
     * 
     * @param name the searched name
     * @return List<book> amount of books
     *
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> searchByName(@RequestParam("name") String name) {
        logger.info("search all book with name: {}", name);
        List<Book> books = bookService.searchBookByName(name);
        logger.info("total book search result with name: {}", books.size());
        return new ResponseEntity<>(
                books.stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Search books by name Restful api
     * </p>
     * 
     * @param name the searched name
     * @return List<book> amount of books
     * @throws EntityNotFoundException
     *
     */
    @GetMapping("/inventoryId")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> searchByName(@RequestParam("inventoryId") long inventoryId)
            throws EntityNotFoundException {
        logger.info("search all book with inventoryId: {}", inventoryId);
        List<Book> books = bookService.getAllBooksByInventoryId(inventoryId);
        logger.info("total book search result with inventoryId: {}", books.size());
        return new ResponseEntity<>(
                books.stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Get book By Id Restful api
     * </p>
     * 
     * @param id the searched id
     * @return book the searched book
     *
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> getbookById(@PathVariable("id") long id) throws EntityNotFoundException {
        Book bookEntity = bookService.getBookById(id);
        logger.info("Get book by ID: {} result: {}", id, bookEntity);
        return new ResponseEntity<>(bookMapper.convertToDto(bookEntity), HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Create new book Restful api
     * </p>
     * 
     * @param book the new created book
     * @return ResponseEntity<bookEntity>
     * @throws EntityNotFoundException
     *
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createbook(@RequestBody @Valid BookDTO book) throws EntityNotFoundException {
        Book bookEntity = bookMapper.convertToEntity(book);
        logger.info("created book : {}", book.toString());
        return new ResponseEntity<>(bookMapper.convertToDto(bookService.createBook(bookEntity)), HttpStatus.CREATED);
    }

    /**
     * 
     * <p>
     * Update an existing book Restful api
     * </p>
     * 
     * @param id   the updated book id
     * @param book the updated book information
     * @return ResponseEntity<bookEntity>
     *
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> updatebook(@PathVariable("id") long id, @RequestBody @Valid BookDTO book)
            throws EntityNotFoundException, UnmatchIDException {
        if (book.getId() != 0 && id != book.getId()) {
            logger.error("ID in URL and Body don't match");
            throw new UnmatchIDException("ID in URL and Body don't match");
        }
        book.setId(id);
        Book bookEntity = bookMapper.convertToEntity(book);
        return new ResponseEntity<>(bookMapper.convertToDto(bookService.updateBook(bookEntity)), HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Delete a book with id Restful api
     * </p>
     * 
     * @param id the deleted book id
     * @throws CanNotDeleteEntityException 
     *
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletebook(@PathVariable("id") long id) throws CanNotDeleteEntityException {
        logger.info("delete book with id : {}", id);
        bookService.deleteBook(id);
        logger.info("delete book successfully with id : {}", id);
    }
}
