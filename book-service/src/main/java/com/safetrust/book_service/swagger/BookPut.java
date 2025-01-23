package com.safetrust.book_service.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookPut {

    private Long id;

    @NotNull(message = "name should not be empty")
    private String name;

    @NotBlank(message = "author should not be empty")
    private String author;

    @NotBlank(message = "genre should not be empty")
    private String genre;

    @NotBlank(message = "name should not be empty")
    private String code;

    private InventoryPost inventory;
}
