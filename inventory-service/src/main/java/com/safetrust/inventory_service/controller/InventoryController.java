package com.safetrust.inventory_service.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
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
import com.safetrust.inventory_service.entity.Inventory;
import com.safetrust.inventory_service.exception.CanNotDeleteEntityException;
import com.safetrust.inventory_service.exception.EntityNotFoundException;
import com.safetrust.inventory_service.exception.UnmatchIDException;
import com.safetrust.inventory_service.mapper.InventoryMapper;
import com.safetrust.inventory_service.model.BookDTO;
import com.safetrust.inventory_service.model.InventoryDTO;
import com.safetrust.inventory_service.client.IBookService;
import com.safetrust.inventory_service.service.IInventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    private IInventoryService inventoryService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 
     * <p>
     * Get pagination inventorys Restful api
     * </p>
     * 
     * @param page the offset that the index of first member
     * @param size the quantity of inventorys
     * @return List<inventory> amount of inventorys
     *
     */
    @GetMapping("pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InventoryDTO>> getinventorys(@PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        logger.info("Get list inventory with offset: {}, pageSize: {} ", offset, pageSize);
        Page<Inventory> inventorys = inventoryService.getInventoryList(offset, pageSize);
        logger.info("list inventory object query: {} ", inventoryService.toString());
        return new ResponseEntity<>(
                inventorys.stream().map(inventory -> inventoryMapper.convertToDto(inventory))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Search inventorys by name Restful api
     * </p>
     * 
     * @param name the searched name
     * @return List<inventory> amount of inventorys
     *
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InventoryDTO>> searchByName(@RequestParam("name") String name) {
        logger.info("search all inventory with name: {}", name);
        List<Inventory> inventorys = inventoryService.searchInventoryByName(name);
        logger.info("total inventory search result with name: {}", inventorys.size());
        return new ResponseEntity<>(
                inventorys.stream().map(inventory -> inventoryMapper.convertToDto(inventory))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Get inventory By Id Restful api
     * </p>
     * 
     * @param id the searched id
     * @return inventory the searched inventory
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     *
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable("id") long id)
            throws EntityNotFoundException, InterruptedException, ExecutionException, TimeoutException {
        InventoryDTO inventoryDto = inventoryMapper.convertToDto(inventoryService.getInventoryById(id));
        List<BookDTO> books = bookService.searchAllByInventoryId(id);
        inventoryDto.setBooks(books);
        logger.info("Get inventory by ID: {} result: {}", id, inventoryDto);
        return new ResponseEntity<>(inventoryDto, HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Create new inventory Restful api
     * </p>
     * 
     * @param inventory the new created inventory
     * @return ResponseEntity<inventoryEntity>
     *
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody @Valid InventoryDTO inventory) {
        Inventory inventoryEntity = inventoryMapper.convertToEntity(inventory);
        logger.info("created inventory : {}", inventory.toString());
        return new ResponseEntity<>(inventoryMapper.convertToDto(inventoryService.createInventory(inventoryEntity)),
                HttpStatus.CREATED);
    }

    /**
     * 
     * <p>
     * Update an existing inventory Restful api
     * </p>
     * 
     * @param id        the updated inventory id
     * @param inventory the updated inventory information
     * @return ResponseEntity<inventoryEntity>
     *
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InventoryDTO> updateinventory(@PathVariable("id") long id,
            @RequestBody @Valid InventoryDTO inventory)
            throws EntityNotFoundException, UnmatchIDException {
        if (inventory.getId() != 0 && id != inventory.getId()) {
            logger.error("ID in URL and Body don't match");
            throw new UnmatchIDException("ID in URL and Body don't match");
        }
        inventory.setId(id);
        Inventory inventoryEntity = inventoryMapper.convertToEntity(inventory);
        return new ResponseEntity<>(inventoryMapper.convertToDto(inventoryService.updateInventory(inventoryEntity)),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Delete a inventory with id Restful api
     * </p>
     * 
     * @param id the deleted inventory id
     * @throws CanNotDeleteEntityException
     *
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteinventory(@PathVariable("id") long id) throws CanNotDeleteEntityException {
        logger.info("delete inventory with id : {}", id);
        inventoryService.deleteInventory(id);
        logger.info("delete inventory successfully with id : {}", id);
    }

    public void sendToKafka(Long id, String topic) throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<SendResult<String, Long>> future = kafkaTemplate.send(topic, id);

        future.whenComplete((result, ex) -> {
        if (ex == null) {
            System.out.println(result.toString());
        } else {
            System.out.println("exceoption=============");
        }
        });
    }
}
