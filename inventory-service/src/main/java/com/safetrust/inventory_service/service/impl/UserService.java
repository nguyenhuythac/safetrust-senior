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

import com.safetrust.inventory_service.entity.User;
import com.safetrust.inventory_service.repository.UserRepository;
import com.safetrust.inventory_service.service.IUserService;

import com.safetrust.inventory_service.exception.EntityNotFoundException;

@Service
public class UserService implements IUserService{
    Logger logger  = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> getUserList(int page, int size) {
        PageRequest pageReq = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageReq);
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) throws EntityNotFoundException {
        Optional<User> User = userRepository.findById(id);
        if(User.isPresent() ){
            return User.get();
        } else {
            logger.error("User is not existed with id: {}", id);
            throw new EntityNotFoundException("User is not existed with id: " + id);
        }
    }

    @Override
    public User updateUser(User user) throws EntityNotFoundException {
        long id = user.getId();
        try {
            return userRepository.save(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("User is not existed with id: {}, {}", id, e);
            throw new EntityNotFoundException("User is not existed with id: " + id);
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> searchUserByName(String name) {
        return null;
    }
    
}
