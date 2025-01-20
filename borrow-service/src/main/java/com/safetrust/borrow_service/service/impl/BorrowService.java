package com.safetrust.borrow_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safetrust.borrow_service.client.ClientAsynService;
import com.safetrust.borrow_service.client.IBookService;
import com.safetrust.borrow_service.client.IUserService;
import com.safetrust.borrow_service.entity.Borrow;
import com.safetrust.borrow_service.exception.CanNotDeleteEntityException;
import com.safetrust.borrow_service.exception.EntityNotFoundException;
import com.safetrust.borrow_service.exception.UnmatchIDException;
import com.safetrust.borrow_service.mapper.BorrowMapper;
import com.safetrust.borrow_service.model.BookDTO;
import com.safetrust.borrow_service.model.BorrowDTO;
import com.safetrust.borrow_service.model.UserDTO;
import com.safetrust.borrow_service.repository.BorrowRepository;
import com.safetrust.borrow_service.service.IBorrowService;
import com.safetrust.borrow_service.status.EBookStatus;
import com.safetrust.borrow_service.status.EBorrowStatus;
import com.safetrust.borrow_service.status.ETrackingUser;

import feign.FeignException;

@Service
@Transactional
public class BorrowService implements IBorrowService{
    private Logger logger  = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BorrowRepository borrowRepo;

    @Autowired
    private ClientAsynService clientAsynService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Autowired
    private BorrowMapper borrowMapper;

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
    public Borrow createBorrow(Borrow borrow) throws Exception {
        try {
            BookDTO book = bookService.getbookById(borrow.getBook().getId()).getBody();
            if(!EBookStatus.AVAILABLE.equals(book.getStatus())){
                logger.error("Book is not available with bookId: {} ", book.getId());
                throw new EntityNotFoundException("Book is not available to borrow with bookId: " + book.getId());
            }
            UserDTO user = userService.getUserById(borrow.getUser().getId()).getBody();
            clientAsynService.updatebookAfterBrowing(book, EBookStatus.BORROWING.getValue());
            clientAsynService.updateUserAfterBrowing(user, ETrackingUser.BORROWED.getValue());
            return borrowRepo.save(borrow);
        } catch (DataIntegrityViolationException e) {
            long bookId = borrow.getBook().getId();
            long userId = borrow.getUser().getId();
            logger.error("Book or user is not existed with bookId: {} and userId: {}", bookId, userId, e);
            throw new EntityNotFoundException("Book or user is not existed with bookId: " + bookId + " and userId: " + userId);
        } catch(FeignException e){
            logger.error(e.getMessage());
            throw new EntityNotFoundException(e.getMessage());
        }
        
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
    public void updateBorrowStatusToDone(long id) throws EntityNotFoundException, UnmatchIDException {
        try {
            BorrowDTO borrow = borrowMapper.convertToDto(getBorrowById(id));
            clientAsynService.updatebookAfterBrowing(borrow.getBook(), EBookStatus.AVAILABLE.getValue());
            clientAsynService.updateUserAfterBrowing(borrow.getUser(), ETrackingUser.RETURNED.getValue());
            borrowRepo.updateBorrowStatusToDone(id, EBorrowStatus.DONE);
        } catch (JpaObjectRetrievalFailureException e) {
            logger.error("Borrow or user is not existed with bookId: {} and userId: {}", id, e);
            throw new EntityNotFoundException("Borrow or user is not existed with bookId: " + id);
        }
         catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Borrow is not existed with id: {}, {}", id, e);
            throw new EntityNotFoundException("Borrow is not existed with id: " + id);
        }
    }

    @Override
    public void deleteBorrow(long id) throws CanNotDeleteEntityException {
        try {
            borrowRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("User can't be delete because relationship with id: {}, {}", id, e);
            throw new CanNotDeleteEntityException("User can't be delete because relationship with id: " + id);
        }
        
    }

    // private void getBookAndUsser(BookDTO book, UserDTO user) throws Exception {
    //     CompletableFuture<String> bookServiceResponse  = clientAsynService.findBookById(book.getId(), book);
         
    //     CompletableFuture<String> userServiceResponse = clientAsynService.findUserById(user.getId(), user);
        
    //     CompletableFuture<String> completableFuture = bookServiceResponse
    //             .thenComposeAsync(engWordServiceValue -> userServiceResponse
    //                     .thenApplyAsync(ielsWordServiceValue -> engWordServiceValue + ielsWordServiceValue));
    //     while (true) {
    //         if(completableFuture.isDone()){
    //             if(completableFuture.isCompletedExceptionally()){
    //                 logger.error("Exception Asyn");
    //                 throw new Exception("Exception Asyn");
    //             } 
    //             break;
    //         }
    //     }
    // }
    
}
