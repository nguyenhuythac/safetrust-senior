package com.safetrust.user_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.safetrust.user_service.model.BookDTO;

@FeignClient(name = "book-service", url = "http://localhost:8082/book")
public interface IBookService {

    @GetMapping("/inventory/{inventoryId}")
    List<BookDTO> searchAllByInventoryId(@PathVariable("inventoryId") long inventoryId);
}
