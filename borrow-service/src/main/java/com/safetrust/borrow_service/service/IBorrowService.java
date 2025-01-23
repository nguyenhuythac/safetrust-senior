package com.safetrust.borrow_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.safetrust.borrow_service.entity.Borrow;
import com.safetrust.borrow_service.exception.CanNotDeleteEntityException;
import com.safetrust.borrow_service.exception.EntityNotFoundException;
import com.safetrust.borrow_service.exception.UnmatchIDException;
import com.safetrust.borrow_service.status.EBookStatus;
import com.safetrust.borrow_service.status.ETrackingUser;

public interface IBorrowService {
    /**
     * 
     * <p>
     * Get pagination contact list
     * </p>
     * 
     * @param page the offset that the index of first member
     * @param size the quantity of contacts
     * @return Page that is the amount of contacts
     *
     */
    Page<Borrow> getBorrowsList(int page, int size);

    /**
     * 
     * <p>
     * Get all contact list
     * </p>
     * 
     * @return the amount of all contacts
     *
     */
    List<Borrow> getAllBorrows();

    /**
     * 
     * <p>
     * Create new contact
     * </p>
     * 
     * @param contactEntity the new contact information
     * @return ContactEntity the new created contact
     * @throws EntityNotFoundException
     * @throws Exception
     *
     */
    Borrow createBorrow(Borrow borrow) throws EntityNotFoundException, Exception;

    /**
     * 
     * <p>
     * Get contact by Id
     * </p>
     * 
     * @param id the id that is used to find contact
     * @return ContactEntity the contact that is found
     *
     */
    Borrow getBorrowById(long id) throws EntityNotFoundException;

    /**
     * 
     * <p>
     * Update Contact
     * </p>
     * 
     * @param returned
     * @param available
     * @param contactEntity the contact information
     * @return ContactEntity the updated contact
     * @throws UnmatchIDException
     *
     */
    void updateBorrowStatus(long id, EBookStatus available, ETrackingUser returned)
            throws EntityNotFoundException, UnmatchIDException;

    /**
     * 
     * <p>
     * Delete Contact
     * </p>
     * 
     * @param id the id that is deleted
     * @throws CanNotDeleteEntityException
     *
     */
    void deleteBorrow(long id) throws CanNotDeleteEntityException;
}
