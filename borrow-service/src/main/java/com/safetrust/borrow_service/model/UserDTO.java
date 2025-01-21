package com.safetrust.borrow_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetrust.borrow_service.status.ETrackingUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username should not be empty")
    private String username;

    @NotBlank(message = "Phone number should not be empty")
    @Pattern(regexp = "^0\\d{9}$", message = "Wrong phone number format")
    private String phone;

    @NotBlank(message = "Address should not be empty")
    private String address;        
    private ETrackingUser tracking;
    private Date createdDate;
    private Integer borowedTotal; 

    @JsonIgnoreProperties("createdBrandUsers")
    private InventoryDTO created_inventory;

    @JsonIgnoreProperties("user")
    private List<BorrowDTO> borrows;
}
