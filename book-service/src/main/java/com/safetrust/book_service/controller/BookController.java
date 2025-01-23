package com.safetrust.book_service.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.safetrust.book_service.entity.Book;
import com.safetrust.book_service.exception.CanNotDeleteEntityException;
import com.safetrust.book_service.exception.EntityNotFoundException;
import com.safetrust.book_service.exception.UnmatchIDException;
import com.safetrust.book_service.mapper.BookMapper;
import com.safetrust.book_service.model.BookDTO;
import com.safetrust.book_service.model.InventoryDTO;
import com.safetrust.book_service.service.IBookService;
import com.safetrust.book_service.status.EBookStatus;
import com.safetrust.book_service.status.EReportType;
import com.safetrust.book_service.swagger.BookPost;
import com.safetrust.book_service.swagger.BookPut;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * 
 * book controller Rest api class.
 * 
 * @author Thac Nguyen
 */
@RestController
@RequestMapping("/book")
@Tag(name = "BOOK", description = "Everything about library book")
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
    @Operation(summary = "Get all books pagination", description = "Get all books pagination")
    @GetMapping("pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> getbooks(
            @Parameter(description = "Book get from", required = true) @PathVariable("offset") int offset,
            @Parameter(description = "searching Book quantity", required = true) @PathVariable("pageSize") int pageSize) {
        logger.info("Get list book with offset: {}, pageSize: {} ", offset, pageSize);
        Page<Book> books = bookService.getBookList(offset, pageSize);
        logger.info("list book object query: {} ", bookService.toString());
        return new ResponseEntity<>(
                books.stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList()),
                HttpStatus.OK);
    }


    @Operation(summary = "Search all books by name, author or genre", description = "Search all books by name, author or genre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid Argument", content = @Content) })
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> searchByName(
            @Parameter(description = "searching value", required = true) @RequestParam("name") String name,
            @Parameter(description = "searching key include: name, author, genre", required = true) @RequestParam("by") String by)
            throws UnmatchIDException {
        logger.info("search all book with name: {}", name);
        List<Book> books = new ArrayList<>();
        if ("author".equals(by)) {
            books = bookService.searchBookByAuthor(name);
        } else if ("genre".equals(by)) {
            books = bookService.searchBookByGenre(name);
        } else {
            books = bookService.searchBookByName(name);
        }

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
    @Operation(summary = "Get all book by inventory id", description = "Get all book by inventory id")
    @GetMapping("/inventory/{inventoryId}")
    public List<BookDTO> searchAllByInventoryId(
            @Parameter(description = "Inventory id", required = true) @PathVariable("inventoryId") long inventoryId)
            throws EntityNotFoundException {
        logger.info("search all book with inventoryId: {}", inventoryId);
        List<Book> books = bookService.getAllBooksByInventoryId(inventoryId);
        logger.info("total book search result with inventoryId: {}", books.size());
        return books.stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList());
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
    @Operation(summary = "Get book by id", description = "Get book by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> getbookById(@PathVariable("id") long id) throws EntityNotFoundException {
        Book bookEntity = bookService.getBookById(id);
        logger.info("Get book by ID: {} result: {}", id, bookEntity);
        return new ResponseEntity<>(bookMapper.convertToDto(bookEntity), HttpStatus.OK);
    }

    @Operation(summary = "Report search all book include: findbestbook, findoverdue, countbook", 
                description = "Report search all book include: findbestbook, findoverdue, countbook")
    @GetMapping("/report/{report}")
    public List<BookDTO> getBookReport(
            @Parameter(description = "Report type include: findbestbook, findoverdue, countbook", required = true) 
            @PathVariable("report") String report)
            throws UnmatchIDException {
        List<Book> books = new ArrayList<>();
        if (EReportType.FIND_BEST_BORROWED_BOOK.getValue().equals(report)) {
            logger.info("find best borrow book per inventory");
            books = bookService.findBestBooksByOfPerInventory();
        } else if (EReportType.FIND_OVERDUE_BOOK.getValue().equals(report)) {
            logger.info("find all overdue book per inventory");
            books = bookService.findOverdueBooksByOfPerInventory();
        } else {
            logger.error("wrong report in URL, {}", report);
            throw new UnmatchIDException("report in URL: " + report);
        }
        return books.stream().map(book -> bookMapper.convertToDto(book))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Report count all available book per inventory", description = "Report count all available book per inventory")
    @GetMapping("/report/countbook")
    public Map<String, Long> getBookReportAvaille() throws UnmatchIDException {
        logger.info("find all overdue book per inventory");
        return bookService.findAvailableBooksByOfPerInventory();
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
     * @throws UnmatchIDException
     *
     */
    @Operation(summary = "Create a new book", description = "Create a new book")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = BookPost.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid Argument", content = @Content) ,
            @ApiResponse(responseCode = "500", description = "UnmatchIDException", content = @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createbook(@RequestBody @Valid BookDTO book)
            throws EntityNotFoundException, UnmatchIDException {
        InventoryDTO inventory = book.getInventory();
        book.setStatus(EBookStatus.AVAILABLE);
        book.setBorrowedTotal(0);
        book.setBorrowedDate(new Date());
        if (book == null || inventory == null || inventory.getId() == null) {
            throw new UnmatchIDException("inventory and inventoryId can't not be");
        }
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
    @Operation(summary = "Update book By id", description = "Update book By id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = BookPut.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookPut.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid Argument", content = @Content),
            @ApiResponse(responseCode = "500", description = "UnmatchIDException", content = @Content) })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> updatebook(@RequestBody @Valid BookDTO book)
            throws EntityNotFoundException, UnmatchIDException {
        if (book == null || book.getId() == null) {
            logger.error("ID in URL and Body don't match");
            throw new UnmatchIDException("ID in URL and Body don't match");
        }
        Book bookEntity = bookMapper.convertToEntity(book);
        return new ResponseEntity<>(bookMapper.convertToDto(bookService.updateBook(bookEntity)), HttpStatus.OK);
    }

    @Operation(summary = "Update book status after borrowed",description = "Update book status after borrowed")
    @PutMapping("/{status}/{id}/{total}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> updatebookAfterBrowing(
            @Parameter(description = "book id", required = true) @PathVariable("id") long id,
            @Parameter(description = "update status include: available, borrowing, overdue, ", required = true) @PathVariable("status") String status,
            @Parameter(description = "borrowed total per book", required = true) @PathVariable("total") int total)
            throws EntityNotFoundException, UnmatchIDException {
        if (EBookStatus.BORROWING.getValue().equals(status)) {
            bookService.updatebookAfter(id, total + 1, EBookStatus.BORROWING);
        } else if (EBookStatus.AVAILABLE.getValue().equals(status)) {
            bookService.updatebookAfter(id, total, EBookStatus.AVAILABLE);
        } else if (EBookStatus.OVERDUE.getValue().equals(status)) {
            bookService.updatebookAfter(id, total, EBookStatus.OVERDUE);
        } else {
            logger.error("status in URL and Body don't match");
            throw new UnmatchIDException("status in URL and Body don't match");
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
    @Operation(summary = "Delete book by id", description = "Delete book by id")
    @ApiResponses({ @ApiResponse(responseCode = "400", description = "Invalid Argument", content = @Content) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletebook(@PathVariable("id") long id) throws CanNotDeleteEntityException {
        logger.info("delete book with id : {}", id);
        bookService.deleteBook(id);
        logger.info("delete book successfully with id : {}", id);
    }
}
