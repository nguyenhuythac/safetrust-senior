package com.safetrust.report_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryDTO {
    private Long id;
    private String name;
    private String address;

    @JsonIgnore
    @JsonIgnoreProperties("inventory")
    private List<BookDTO> books;

    @JsonIgnore
    @JsonIgnoreProperties("created_inventory")
    private List<UserDTO> createdBrandUsers;
}
