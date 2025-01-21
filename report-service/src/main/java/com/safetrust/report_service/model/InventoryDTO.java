package com.safetrust.report_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
