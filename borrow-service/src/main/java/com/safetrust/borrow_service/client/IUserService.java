package com.safetrust.borrow_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.safetrust.borrow_service.exception.EntityNotFoundException;
import com.safetrust.borrow_service.exception.UnmatchIDException;
import com.safetrust.borrow_service.model.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8083/user")
public interface IUserService {

    @PutMapping("/{status}/{id}/{total}")
    ResponseEntity<UserDTO> updateuser(@PathVariable("id") long id, @PathVariable("status") String status,
            @PathVariable("total") int total)
            throws EntityNotFoundException, UnmatchIDException;

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) throws EntityNotFoundException;
}
