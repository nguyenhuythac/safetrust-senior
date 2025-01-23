package com.safetrust.user_service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safetrust.user_service.entity.User;
import com.safetrust.user_service.exception.CanNotDeleteEntityException;
import com.safetrust.user_service.exception.EntityNotFoundException;
import com.safetrust.user_service.repository.UserRepository;
import com.safetrust.user_service.service.IUserService;
import com.safetrust.user_service.status.ETrackingUser;

@Service
@Transactional
public class UserService implements IUserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

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
    public User createUser(User user) throws EntityNotFoundException {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            long inventoryId = user.getCreated_inventory().getId();
            logger.error("Inventory is not existed with id: {}, {}", inventoryId, e);
            throw new EntityNotFoundException("Inventory is not existed with id: " + inventoryId);
        }

    }

    @Override
    public User getUserById(long id) throws EntityNotFoundException {
        Optional<User> User = userRepository.findById(id);
        if (User.isPresent()) {
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
        } catch (JpaObjectRetrievalFailureException e) {
            long inventoryId = user.getCreated_inventory().getId();
            logger.error("Inventory is not existed with id: {}, {}", inventoryId, e);
            throw new EntityNotFoundException("Inventory is not existed with id: " + inventoryId);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("User is not existed with id: {}, {}", id, e);
            throw new EntityNotFoundException("User is not existed with id: " + id);
        } catch (Exception e) {
            logger.error("User is not existed with id: {}, {}", id, e);
            throw new EntityNotFoundException("Inventory is not existed with id: " + user.getCreated_inventory().getId());
        }
    }

    @Override
    public void deleteUser(long id) throws CanNotDeleteEntityException {
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("User can't be delete because relationship with id: {}, {}", id, e);
            throw new CanNotDeleteEntityException("User can't be delete because relationship with id: " + id);
        }

    }

    @Override
    public List<User> searchUserByName(String name) {
        return null;
    }

    @Override
    public void updateUserStatus(long id, int total, ETrackingUser status) {
        userRepository.updateUserStatus(id, total, status);
    }

    @Override
    public Map<String, Long> findAvailableUsersByOfPerInventory() {
        Map<String, Long> result = new HashMap<>();
        List<Object[]> countList = userRepository.findAvailableUserByOfPerInventory(ETrackingUser.FINES);
        for (Object[] count : countList) {
            result.put((String) count[0], (Long) count[1]);
        }
        return result;
    }

}
