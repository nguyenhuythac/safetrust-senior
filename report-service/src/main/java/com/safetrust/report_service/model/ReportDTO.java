package com.safetrust.report_service.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportDTO {

    private Map<String, Map<String, Integer>> mostBorrowedBookPerBranch = new HashMap<>();
    private Map<String, List<String>> overdueBookPerBranch= new HashMap<>();
    private Map<String, Long> totalAvailableBookPerBranch = new HashMap<>();
    private Map<String, Long> totalAvailableUserPerBranch = new HashMap<>();

}
