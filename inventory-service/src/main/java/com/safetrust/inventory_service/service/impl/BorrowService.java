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

import com.safetrust.inventory_service.entity.Borrow;
import com.safetrust.inventory_service.repository.BorrowRepository;
import com.safetrust.inventory_service.service.IBorrowService;

import com.safetrust.inventory_service.exception.EntityNotFoundException;

@Service
public class BorrowService implements IBorrowService{
    private Logger logger  = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BorrowRepository borrowRepo;

    @Override
    public Page<Borrow> getBorrowsList(int page, int size) {
        PageRequest pageReq = PageRequest.of(page, size);
        Page<Borrow> borrows = borrowRepo.findAll(pageReq);
        return borrows;
    }

    @Override
    public List<Borrow> getAllBorrows() {
       return borrowRepo.findAll();
    }

    @Override
    public Borrow createBorrow(Borrow borrow) {
        return borrowRepo.save(borrow);
    }

    @Override
    public Borrow getBorrowById(long id) throws EntityNotFoundException {
        Optional<Borrow> borrowEntity = borrowRepo.findById(id);
        if(borrowEntity.isPresent() ){
            return borrowEntity.get();
        } else {
            logger.error("Borrow is not existed with id: {}", id);
            throw new EntityNotFoundException("Borrow is not existed with id: " + id);
        }
    }

    @Override
    public Borrow updateBorrow(Borrow borrow) throws EntityNotFoundException {
         long id = borrow.getId();
        try {
            return borrowRepo.save(borrow);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Borrow is not existed with id: {}, {}", id, e);
            throw new EntityNotFoundException("Borrow is not existed with id: " + id);
        }
    }

    @Override
    public void deleteBorrow(long id) {
        borrowRepo.deleteById(id);
    }

    @Override
    public List<Borrow> searchBorrowByName(String name) {
       return null;
    }

    
    
}
