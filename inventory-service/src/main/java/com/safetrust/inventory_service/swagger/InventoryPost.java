package com.safetrust.inventory_service.swagger;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryPost {
    @NotBlank(message = "name should not be empty")
    private String name;

    @NotBlank(message = "name should not be empty")
    private String address;
}
