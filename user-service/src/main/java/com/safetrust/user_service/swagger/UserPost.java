package com.safetrust.user_service.swagger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPost {

    @NotBlank(message = "Username should not be empty")
    private String username;

    @NotBlank(message = "Phone number should not be empty")
    @Pattern(regexp = "^0\\d{9}$", message = "Wrong phone number format")
    private String phone;

    @NotBlank(message = "Address should not be empty")
    private String address;

    @JsonIgnoreProperties("createdBrandUsers")
    private InventoryPost created_inventory;
}
