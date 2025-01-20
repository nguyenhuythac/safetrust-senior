package com.safetrust.book_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;

    @NotBlank(message = "name should not be empty")
    private String name;

    @NotBlank(message = "name should not be empty")
    private String address;
    
    @JsonIgnoreProperties("inventory")
    private List<BookDTO> books;

    private List<UserDTO> createdBrandUsers;
}
