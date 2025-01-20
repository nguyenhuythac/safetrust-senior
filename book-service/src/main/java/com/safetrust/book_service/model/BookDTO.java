package com.safetrust.book_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safetrust.book_service.status.EBookStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;

    @NotNull(message = "name should not be empty")
    private String name;

    @NotBlank(message = "author should not be empty")
    private String author;

    @NotBlank(message = "genre should not be empty")
    private String genre;

    private Integer BorrowedTotal;

    @NotBlank(message = "name should not be empty")
    private String code;

    private EBookStatus status;

    private Date borrowedDate;

    @JsonIgnoreProperties("books")
    private InventoryDTO inventory;

    @JsonIgnore
    private List<BorrowDTO> borrows;
}
