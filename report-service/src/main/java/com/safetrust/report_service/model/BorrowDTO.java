package com.safetrust.report_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetrust.report_service.status.EBorrowStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowDTO {
    private Long id;
    private EBorrowStatus status = EBorrowStatus.BORROWING;

    @JsonIgnoreProperties("borrows")
    private UserDTO user;

    @JsonIgnoreProperties("borrows")
    private BookDTO book;

}
