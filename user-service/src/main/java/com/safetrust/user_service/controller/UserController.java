package com.safetrust.user_service.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.safetrust.user_service.swagger.UserPost;
import com.safetrust.user_service.swagger.UserPut;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "USER", description = "Everything about library user")
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
    @Operation(summary = "Get all users pagination", description = "Get users books pagination")
    @GetMapping("pagination/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> getusers(
            @Parameter(description = "Getting user from", required = true) @PathVariable("offset") int offset,
            @Parameter(description = "searching user quantity", required = true) @PathVariable("pageSize") int pageSize) {
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
     * Get user By Id Restful api
     * </p>
     * 
     * @param id the searched id
     * @return user the searched user
     *
     */
    @Operation(summary = "Get user by id", description = "Get user by id")
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
    @Operation(summary = "Create a new user", description = "Create a new user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserPost.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid Argument", content = @Content) ,
            @ApiResponse(responseCode = "500", description = "UnmatchIDException", content = @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createuser(@RequestBody @Valid UserDTO userDto)
            throws EntityNotFoundException, UnmatchIDException {
        InventoryDTO createdIdnventory = userDto.getCreated_inventory();
        if (userDto == null || createdIdnventory == null || createdIdnventory.getId() == null) {
            throw new UnmatchIDException("inventory and inventoryId can't not be");
        }
        userDto.setTracking(ETrackingUser.FINES);
        userDto.setCreatedDate(new Date());
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
    @Operation(summary = "Update book By id", description = "Update book By id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserPut.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserPut.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid Argument", content = @Content),
            @ApiResponse(responseCode = "500", description = "UnmatchIDException", content = @Content) })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateuser(@RequestBody @Valid UserDTO userDto)
            throws EntityNotFoundException, UnmatchIDException {
        if (userDto == null || userDto.getId() == null) {
            logger.error("ID Body can not empty");
            throw new UnmatchIDException("ID Body can not empty");
        }
        User userEntity = userMapper.convertToEntity(userDto);
        return new ResponseEntity<>(userMapper.convertToDto(userService.updateUser(userEntity)), HttpStatus.OK);
    }

    @Operation(summary = "Update User status after borrowed",description = "Update User status after borrowed")
    @PutMapping("/{status}/{id}/{total}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateuser(
            @Parameter(description = "user id", required = true) @PathVariable("id") long id,
            @Parameter(description = "update user status include: borrowed, returned, overdued ", required = true) @PathVariable("status") String status,
            @Parameter(description = "borrowed total per user", required = true) @PathVariable("total") int total)
            throws EntityNotFoundException, UnmatchIDException {
        if (ETrackingUser.BORROWED.getValue().equals(status)) {
            userService.updateUserStatus(id, total + 1, ETrackingUser.BORROWED);
        } else if (ETrackingUser.RETURNED.getValue().equals(status)) {
            userService.updateUserStatus(id, total, ETrackingUser.RETURNED);
        } else if (ETrackingUser.OVERDUED.getValue().equals(status)) {
            userService.updateUserStatus(id, total, ETrackingUser.OVERDUED);
        } else {
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
    @Operation(summary = "Delete user by id", description = "Delete user by id")
    @ApiResponses({ @ApiResponse(responseCode = "500", description = "Can not delete user because relationship", content = @Content) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteuser(@PathVariable("id") long id) throws CanNotDeleteEntityException {
        logger.info("delete user with id : {}", id);
        userService.deleteUser(id);
        logger.info("delete user successfully with id : {}", id);
    }

    @Operation(summary = "Report count all available user per inventory", 
                description = "Report count all available book per inventory")
    @GetMapping("/report/countUser")
    public Map<String, Long> getUserReportAvaille() throws UnmatchIDException {
        logger.info("find all available user per inventory");
        return userService.findAvailableUsersByOfPerInventory();
    }
}
