package com.safetrust.borrow_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safetrust.borrow_service.status.EBorrowStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDTO {
    private Long id;
    private EBorrowStatus status = EBorrowStatus.BORROWING; 

    @JsonIgnoreProperties("borrows")
    private UserDTO user;

    @JsonIgnoreProperties("borrows")
    private BookDTO book;
    
}
