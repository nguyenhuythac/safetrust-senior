package com.safetrust.report_service.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.safetrust.report_service.exception.UnmatchIDException;
import com.safetrust.report_service.model.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8083/user")
public interface IUserService {

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id);

    @GetMapping("/report/countUser")
    Map<String, Long> getUserReportAvaille() throws UnmatchIDException;
}
