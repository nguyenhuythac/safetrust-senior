package com.safetrust.borrow_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.safetrust.borrow_service.exception.EntityNotFoundException;
import com.safetrust.borrow_service.exception.UnmatchIDException;
import com.safetrust.borrow_service.model.BookDTO;
import com.safetrust.borrow_service.model.UserDTO;


@Service
public class ClientAsynService {
    
    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Async
    public void updatebookAfterBrowing(BookDTO book, String status) throws EntityNotFoundException, UnmatchIDException{
        bookService.updatebookAfterBrowing(book.getId(), status, book.getBorrowedTotal());
    }

    @Async
    public void updateUserAfterBrowing(UserDTO user, String status) throws EntityNotFoundException, UnmatchIDException{
        userService.updateuser(user.getId(), status, user.getBorowedTotal());
    }
}
