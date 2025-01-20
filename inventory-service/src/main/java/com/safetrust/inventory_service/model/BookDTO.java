package com.safetrust.inventory_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;

    @NotBlank(message = "name should not be empty")
    private String name;

    @NotBlank(message = "code should not be empty")
    private String code;

    @NotBlank(message = "status should not be empty")
    private String status;

    private Date borrowedDate;

    @JsonIgnoreProperties("books")
    private InventoryDTO inventory;

    @JsonIgnore
    private List<BorrowDTO> borrows;
}
