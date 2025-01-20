package com.safetrust.user_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String name;
    private String code;
    private String status;
    private Date borrowedDate;

    @JsonIgnore
    private List<BorrowDTO> borrows;
}
