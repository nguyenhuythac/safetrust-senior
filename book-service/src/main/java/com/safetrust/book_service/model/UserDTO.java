package com.safetrust.book_service.model;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username should not be empty")
    private String username;

    @NotBlank(message = "Phone number should not be empty")
    @Pattern(regexp = "^0\\d{9}$", message = "Wrong phone number format")
    private String phone;

    @NotBlank(message = "Address should not be empty")
    private String address; 
        
    private String tracking;
    private Date createdDate;

    private InventoryDTO created_inventory_id;
    private List<BorrowDTO> borrows;
}
