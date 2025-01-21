package com.safetrust.user_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetrust.user_service.entity.User;
import com.safetrust.user_service.exception.CanNotDeleteEntityException;
import com.safetrust.user_service.exception.EntityNotFoundException;
import com.safetrust.user_service.exception.UnmatchIDException;
import com.safetrust.user_service.mapper.UserMapper;
import com.safetrust.user_service.model.InventoryDTO;
import com.safetrust.user_service.model.UserDTO;
import com.safetrust.user_service.service.IUserService;
import com.safetrust.user_service.status.ETrackingUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    private IUserService userService;

    /**
     * 
     * <p>
     * Get pagination users Restful api
     * </p>
     * 
     * @param page the offset that the index of first member
     * @param size the quantity of users
     * @return List<user> amount of users
     *
     */
    @GetMapping("pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> getusers(@PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        logger.info("Get list user with offset: {}, pageSize: {} ", offset, pageSize);
        Page<User> users = userService.getUserList(offset, pageSize);
        logger.info("list user object query: {} ", userService.toString());
        return new ResponseEntity<>(
                users.stream().map(user -> userMapper.convertToDto(user)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Search users by name Restful api
     * </p>
     * 
     * @param name the searched name
     * @return List<user> amount of users
     *
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> searchByName(@RequestParam("name") String name) {
        logger.info("search all user with name: {}", name);
        List<User> users = userService.searchUserByName(name);
        logger.info("total user search result with name: {}", users.size());
        return new ResponseEntity<>(
                users.stream().map(user -> userMapper.convertToDto(user)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Get user By Id Restful api
     * </p>
     * 
     * @param id the searched id
     * @return user the searched user
     *
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) throws EntityNotFoundException {
        User userEntity = userService.getUserById(id);
        logger.info("Get user by ID: {} result: {}", id, userEntity);
        return new ResponseEntity<>(userMapper.convertToDto(userEntity), HttpStatus.OK);
    }

    /**
     * 
     * <p>
     * Create new user Restful api
     * </p>
     * 
     * @param user the new created user
     * @return ResponseEntity<userEntity>
     * @throws EntityNotFoundException
     * @throws UnmatchIDException
     *
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createuser(@RequestBody @Valid UserDTO userDto)
            throws EntityNotFoundException, UnmatchIDException {
        InventoryDTO createdIdnventory = userDto.getCreated_inventory();
        if (userDto == null || createdIdnventory == null || createdIdnventory.getId() == null) {
            throw new UnmatchIDException("inventory and inventoryId can't not be");
        }
        userDto.setTracking(ETrackingUser.FINES);
        User userEntity = userMapper.convertToEntity(userDto);
        logger.info("created user : {}", userDto.toString());
        return new ResponseEntity<>(userService.createUser(userEntity), HttpStatus.CREATED);
    }
    

    /**
     * 
     * <p>
     * Update an existing user Restful api
     * </p>
     * 
     * @param id   the updated user id
     * @param user the updated user information
     * @return ResponseEntity<userEntity>
     *
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateuser(@PathVariable("id") long id, @RequestBody @Valid UserDTO userDto)
            throws EntityNotFoundException, UnmatchIDException {
        if (userDto == null || userDto.getId() == null || id != userDto.getId()) {
            logger.error("ID in URL and Body don't match");
            throw new UnmatchIDException("ID in URL and Body don't match");
        }
        userDto.setId(id);
        User userEntity = userMapper.convertToEntity(userDto);
        return new ResponseEntity<>(userMapper.convertToDto(userService.updateUser(userEntity)), HttpStatus.OK);
    }

    @PutMapping("/{status}/{id}/{total}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateuser(@PathVariable("id") long id, @PathVariable("status") String status,
            @PathVariable("total") int total)
            throws EntityNotFoundException, UnmatchIDException {
        if (ETrackingUser.BORROWED.getValue().equals(status)){
            userService.updateUserStatus(id, total + 1, ETrackingUser.BORROWED);
        } else if(ETrackingUser.RETURNED.getValue().equals(status)) {
            userService.updateUserStatus(id, total, ETrackingUser.RETURNED);
        } else if(ETrackingUser.OVERDUED.getValue().equals(status)) {
            userService.updateUserStatus(id, total, ETrackingUser.OVERDUED);
        } else{
            logger.error("status in URL and Body don't match");
            throw new UnmatchIDException("status in URL and Body don't match");
        }       
        return new ResponseEntity<>(HttpStatus.OK);        
    }

    /**
     * 
     * <p>
     * Delete a user with id Restful api
     * </p>
     * 
     * @param id the deleted user id
     * @throws CanNotDeleteEntityException
     *
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteuser(@PathVariable("id") long id) throws CanNotDeleteEntityException {
        logger.info("delete user with id : {}", id);
        userService.deleteUser(id);
        logger.info("delete user successfully with id : {}", id);
    }
}
