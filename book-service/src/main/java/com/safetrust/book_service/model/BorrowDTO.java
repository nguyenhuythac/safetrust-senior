package com.safetrust.book_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDTO {
    private Long id;
    private String status; 
    private UserDTO user_id;
    private BookDTO book_id;
    
}
