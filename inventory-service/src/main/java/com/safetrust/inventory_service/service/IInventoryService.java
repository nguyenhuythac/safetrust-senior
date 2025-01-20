package com.safetrust.inventory_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.safetrust.inventory_service.entity.Inventory;
import com.safetrust.inventory_service.exception.CanNotDeleteEntityException;
import com.safetrust.inventory_service.exception.EntityNotFoundException;

public interface IInventoryService {
     /**
    * 
    * <p>Get pagination contact list</p>
    * @param page the offset that the index of first member
    * @param size the quantity of contacts
    * @return Page that is the amount of contacts
    *
    */
    Page<Inventory> getInventoryList(int page, int size);

    /**
    * 
    * <p>Get all contact list</p>
    * @return the amount of all contacts
    *
    */
    List<Inventory> getAllInventorys();

    /**
    * 
    * <p>Create new contact</p>
    * @param contactEntity the new contact information
    * @return ContactEntity the new created contact
    *
    */
    Inventory createInventory(Inventory inventory);

    /**
    * 
    * <p>Get contact by Id</p>
    * @param id the id that is used to find contact
    * @return ContactEntity the contact that is found
    *
    */
    Inventory getInventoryById(long id) throws EntityNotFoundException;

    /**
    * 
    * <p>Update Contact</p>
    * @param contactEntity the contact information
    * @return ContactEntity the updated contact
     * @throws com.safetrust.inventory_service.exception.EntityNotFoundException 
    *
    */
    Inventory updateInventory(Inventory inventory) throws EntityNotFoundException;

    /**
    * 
    * <p>Delete Contact</p>
    * @param id the id that is deleted
     * @throws CanNotDeleteEntityException 
    *
    */
    void deleteInventory(long id) throws CanNotDeleteEntityException;

    /**
    * 
    * <p>Find Contacts by first name or last name</p>
    * @param name the name is used to find contacts
    * @return the amount of contacts
    *
    */
    List<Inventory> searchInventoryByName(String name);
}
