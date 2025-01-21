package com.safetrust.report_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetrust.report_service.status.ETrackingUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String phone;
    private String address;        
    private ETrackingUser tracking;
    private Date createdDate;
    private Integer borowedTotal; 
    private InventoryDTO created_inventory;
    private List<BorrowDTO> borrows;
}
