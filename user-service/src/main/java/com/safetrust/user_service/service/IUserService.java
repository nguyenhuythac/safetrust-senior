package com.safetrust.user_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.safetrust.user_service.entity.User;
import com.safetrust.user_service.exception.CanNotDeleteEntityException;
import com.safetrust.user_service.exception.EntityNotFoundException;
import com.safetrust.user_service.status.ETrackingUser;

public interface IUserService {
     /**
    * 
    * <p>Get pagination contact list</p>
    * @param page the offset that the index of first member
    * @param size the quantity of contacts
    * @return Page that is the amount of contacts
    *
    */
    Page<User> getUserList(int page, int size);

    /**
    * 
    * <p>Get all contact list</p>
    * @return the amount of all contacts
    *
    */
    List<User> getAllUsers();

    /**
    * 
    * <p>Create new contact</p>
    * @param contactEntity the new contact information
    * @return ContactEntity the new created contact
     * @throws EntityNotFoundException 
    *
    */
    User createUser(User user) throws EntityNotFoundException;

    /**
    * 
    * <p>Get contact by Id</p>
    * @param id the id that is used to find contact
    * @return ContactEntity the contact that is found
    *
    */
    User getUserById(long id) throws EntityNotFoundException;

    /**
    * 
    * <p>Update Contact</p>
    * @param contactEntity the contact information
    * @return ContactEntity the updated contact
    *
    */
    User updateUser(User user) throws EntityNotFoundException;

    /**
    * 
    * <p>Delete Contact</p>
    * @param id the id that is deleted
     * @throws CanNotDeleteEntityException 
    *
    */
    void deleteUser(long id) throws CanNotDeleteEntityException;

    /**
    * 
    * <p>Find Contacts by first name or last name</p>
    * @param name the name is used to find contacts
    * @return the amount of contacts
    *
    */
    List<User> searchUserByName(String name);

    void updateUserStatus(long id, int total, ETrackingUser status);
}
