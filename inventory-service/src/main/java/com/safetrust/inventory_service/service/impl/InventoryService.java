package com.safetrust.inventory_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.safetrust.inventory_service.entity.Inventory;
import com.safetrust.inventory_service.exception.CanNotDeleteEntityException;
import com.safetrust.inventory_service.exception.EntityNotFoundException;
import com.safetrust.inventory_service.repository.InventoryRepository;
import com.safetrust.inventory_service.service.IInventoryService;

@Service
public class InventoryService implements IInventoryService {
    private Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryRepository inventoryRepo;

    @Override
    public Page<Inventory> getInventoryList(int page, int size) {
        PageRequest pageReq = PageRequest.of(page, size);
        Page<Inventory> inventories = inventoryRepo.findAll(pageReq);
        return inventories;
    }

    @Override
    public List<Inventory> getAllInventorys() {
        return inventoryRepo.findAll();
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepo.save(inventory);
    }

    @Override
    public Inventory getInventoryById(long id) throws EntityNotFoundException {
        Optional<Inventory> inventoryEntity = inventoryRepo.findById(id);
        if (inventoryEntity.isPresent()) {
            return inventoryEntity.get();
        } else {
            logger.error("Inventory is not existed with id: {}", id);
            throw new EntityNotFoundException("Inventory is not existed with id: " + id);
        }
    }

    @Override
    public Inventory updateInventory(Inventory inventory) throws EntityNotFoundException {
        long id = inventory.getId();
        try {
            return inventoryRepo.save(inventory);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Inventory is not existed with id: {}, {}", id, e);
            throw new EntityNotFoundException("Inventory is not existed with id: " + id);
        }
    }

    @Override
    public void deleteInventory(long id) throws CanNotDeleteEntityException {
        try {
            inventoryRepo.deleteById(id);
        } catch (Exception e) {
            logger.error("Inventory is not existed with id: {}, {}", id, e);
            throw new CanNotDeleteEntityException("Inventory can't be delete because relationship with id: " + id);
        }
    }

    @Override
    public List<Inventory> searchInventoryByName(String name) {
        return null;
    }

}
