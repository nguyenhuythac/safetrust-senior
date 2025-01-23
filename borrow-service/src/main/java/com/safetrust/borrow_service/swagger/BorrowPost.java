package com.safetrust.borrow_service.swagger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowPost {

    @JsonIgnoreProperties("borrows")
    private UserPost user;

    @JsonIgnoreProperties("borrows")
    private BookPost book;

}
