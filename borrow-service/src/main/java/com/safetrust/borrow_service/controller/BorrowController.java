package com.safetrust.borrow_service.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetrust.borrow_service.client.ClientAsynService;
import com.safetrust.borrow_service.entity.Borrow;
import com.safetrust.borrow_service.exception.CanNotDeleteEntityException;
import com.safetrust.borrow_service.exception.EntityNotFoundException;
import com.safetrust.borrow_service.exception.UnmatchIDException;
import com.safetrust.borrow_service.mapper.BorrowMapper;
import com.safetrust.borrow_service.model.BookDTO;
import com.safetrust.borrow_service.model.BorrowDTO;
import com.safetrust.borrow_service.model.UserDTO;
import com.safetrust.borrow_service.service.IBorrowService;

import jakarta.validation.Valid;

/**
 * 
 * borrow controller Rest api class.
 * 
 * @author Thac Nguyen
 */
@RestController
@RequestMapping("/borrow")
public class BorrowController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IBorrowService borrowService;

    @Autowired
    private ClientAsynService clientAsynService;

    @Autowired
    BorrowMapper borrowMapper;

    /**
     * 
     * <p>
     * Get pagination borrows Restful api
     * </p>
     * 
     * @param page the offset that the index of first member
     * @param size the quantity of borrows
     * @return List<borrow> amount of borrows
     *
     */
    @GetMapping("pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BorrowDTO>> getborrows(@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize) {
        logger.info("Get list borrow with offset: {}, pageSize: {} ", offset, pageSize);
        Page<Borrow> borrows = borrowService.getBorrowsList(offset, pageSize);
        logger.info("list borrow object query: {} ", borrowService.toString());
        return new ResponseEntity<>(
                borrows.stream().map(borrow -> borrowMapper.convertToDto(borrow)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Search borrows by name Restful api
     * </p>
     * 
     * @param name the searched name
     * @return List<borrow> amount of borrows
     *
     */
    // @GetMapping("/search")
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<List<BorrowDTO>> searchByName(@RequestParam("name") String name) {
    //     logger.info("search all borrow with name: {}", name);
    //     List<Borrow> borrows = borrowService.searchBorrowByName(name);
    //     logger.info("total borrow search result with name: {}", borrows.size());
    //     return new ResponseEntity<>(
    //             borrows.stream().map(borrow -> borrowMapper.convertToDto(borrow)).collect(Collectors.toList()),
    //             HttpStatus.OK);
    // }

    /**
     * 
     * <p>
     * Get borrow By Id Restful api
     * </p>
     * 
     * @param id the searched id
     * @return borrow the searched borrow
     *
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BorrowDTO> getborrowById(@PathVariable("id") int id) throws EntityNotFoundException {
        Borrow borrowEntity = borrowService.getBorrowById(id);
        logger.info("Get borrow by ID: {} result: {}", id, borrowEntity);
        return new ResponseEntity<>(borrowMapper.convertToDto(borrowEntity),HttpStatus.OK);
    }

    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BorrowDTO> createborrow(@RequestBody @Valid BorrowDTO borrow)
            throws Exception {
        BookDTO book = borrow.getBook();
        UserDTO user = borrow.getUser();
        if (borrow == null || book == null || book.getId() == null 
            || user == null || user.getId() == null) {
            throw new UnmatchIDException("inventory and inventoryId can't not be");
        }
        Borrow borrowEntity = borrowMapper.convertToEntity(borrow);
        logger.info("created borrow : {}", borrow.toString());
        return new ResponseEntity<>(borrowMapper.convertToDto(borrowService.createBorrow(borrowEntity)),
                HttpStatus.CREATED);
    }

    /**
     * 
     * <p>
     * Update an existing borrow Restful api
     * </p>
     * 
     * @param id      the updated borrow id
     * @param borrow the updated borrow information
     * @return ResponseEntity<borrowEntity>
     *
     */
    @PutMapping("/update-done-status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BorrowDTO> updateborrowStatusToDone(@PathVariable("id") long id)
            throws EntityNotFoundException, UnmatchIDException {
        if (id == 0) {
            logger.error("ID in URL and Body don't match");
            throw new UnmatchIDException("ID in URL and Body don't match");
        }
        borrowService.updateBorrowStatusToDone(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Delete a borrow with id Restful api
     * </p>
     * 
     * @param id the deleted borrow id
     * @throws CanNotDeleteEntityException 
     *
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteborrow(@PathVariable("id") int id) throws CanNotDeleteEntityException {
        logger.info("delete borrow with id : {}", id);
        borrowService.deleteBorrow(id);
        logger.info("delete borrow successfully with id : {}", id);
    }

}
