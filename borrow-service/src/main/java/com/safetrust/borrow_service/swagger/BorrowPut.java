package com.safetrust.borrow_service.swagger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowPut {

    @NotBlank
    private Long id;

    @JsonIgnoreProperties("borrows")
    private UserPost user;

    @JsonIgnoreProperties("borrows")
    private BookPost book;
}
