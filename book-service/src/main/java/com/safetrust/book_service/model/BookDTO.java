package com.safetrust.book_service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetrust.book_service.status.EBookStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private Long id;

    @NotNull(message = "name should not be empty")
    private String name;

    @NotBlank(message = "author should not be empty")
    private String author;

    @NotBlank(message = "genre should not be empty")
    private String genre;

    private Integer borrowedTotal;

    @NotBlank(message = "name should not be empty")
    private String code;

    private EBookStatus status;

    private Date borrowedDate;

    @JsonIgnoreProperties("books")
    private InventoryDTO inventory;
}
