package com.safetrust.book_service.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.safetrust.book_service.entity.Book;
import com.safetrust.book_service.exception.EntityNotFoundException;
import com.safetrust.book_service.model.BookDTO;
import com.safetrust.book_service.service.impl.BookService;

@Service
public class KafkaBookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;


    // @KafkaListener(id = "bookGroup", topics = "book")
    // public CompletableFuture<String> listen(long inventoryId) throws EntityNotFoundException, JsonProcessingException {
    //     logger.info("Receive: ", inventoryId);
    //     logger.info("search all book with inventoryId: {}", inventoryId);
    //     Gson gson = new Gson();
    //     List<Book> books = bookService.getAllBooksByInventoryId(inventoryId);
    //     List<BookDTO> bookDtos = new ArrayList<>();
    //     logger.info("total book search result with inventoryId: {}", books.size());
    //     CompletableFuture<String> future = new CompletableFuture<>();
    //     future.complete(gson.toJson(bookDtos, ArrayList.class));
    //     return future;

    // }
}
