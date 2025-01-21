package com.safetrust.report_service.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetrust.report_service.status.EBookStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    private Long id;
    private String name;
    private String code;
    private EBookStatus status;
    private Date borrowedDate;
    private Integer borrowedTotal;

    @JsonIgnoreProperties("books")
    private InventoryDTO inventory;

    @JsonIgnore
    @JsonIgnoreProperties("book")
    private List<BorrowDTO> borrows;
}
