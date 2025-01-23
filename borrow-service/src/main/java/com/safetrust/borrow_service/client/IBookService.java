package com.safetrust.borrow_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.safetrust.borrow_service.exception.EntityNotFoundException;
import com.safetrust.borrow_service.exception.UnmatchIDException;
import com.safetrust.borrow_service.model.BookDTO;

@FeignClient(name = "book-service", url = "http://localhost:8082/book")
public interface IBookService {

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getbookById(@PathVariable("id") long id) throws EntityNotFoundException;

    @PutMapping("/{status}/{id}/{total}")
    ResponseEntity<BookDTO> updatebookAfterBrowing(@PathVariable("id") long id,
            @PathVariable("status") String status, @PathVariable("total") int total)
            throws EntityNotFoundException, UnmatchIDException;
}
