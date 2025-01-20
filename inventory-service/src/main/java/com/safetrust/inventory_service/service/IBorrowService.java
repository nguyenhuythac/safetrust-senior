package com.safetrust.inventory_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.safetrust.inventory_service.entity.Borrow;

import com.safetrust.inventory_service.exception.EntityNotFoundException;

public interface IBorrowService {
    /**
    * 
    * <p>Get pagination contact list</p>
    * @param page the offset that the index of first member
    * @param size the quantity of contacts
    * @return Page that is the amount of contacts
    *
    */
    Page<Borrow> getBorrowsList(int page, int size);

    /**
    * 
    * <p>Get all contact list</p>
    * @return the amount of all contacts
    *
    */
    List<Borrow> getAllBorrows();

    /**
    * 
    * <p>Create new contact</p>
    * @param contactEntity the new contact information
    * @return ContactEntity the new created contact
    *
    */
    Borrow createBorrow(Borrow borrow);

    /**
    * 
    * <p>Get contact by Id</p>
    * @param id the id that is used to find contact
    * @return ContactEntity the contact that is found
    *
    */
    Borrow getBorrowById(long id) throws EntityNotFoundException;

    /**
    * 
    * <p>Update Contact</p>
    * @param contactEntity the contact information
    * @return ContactEntity the updated contact
    *
    */
    Borrow updateBorrow(Borrow borrow) throws EntityNotFoundException;

    /**
    * 
    * <p>Delete Contact</p>
    * @param id the id that is deleted
    *
    */
    void deleteBorrow(long id);

    /**
    * 
    * <p>Find Contacts by first name or last name</p>
    * @param name the name is used to find contacts
    * @return the amount of contacts
    *
    */
    List<Borrow> searchBorrowByName(String name);
}
