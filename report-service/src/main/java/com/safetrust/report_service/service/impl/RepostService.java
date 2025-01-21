package com.safetrust.report_service.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetrust.report_service.client.IBookService;
import com.safetrust.report_service.exception.UnmatchIDException;
import com.safetrust.report_service.model.BookDTO;
import com.safetrust.report_service.model.ReportDTO;
import com.safetrust.report_service.service.IRepostService;
import com.safetrust.report_service.status.EReportType;

@Service
public class RepostService implements IRepostService{
    @Autowired
    private IBookService bookService;

    @Override
    public ReportDTO getBesBookPerInventory() throws UnmatchIDException {
        List<BookDTO> books = bookService.getBookReport(EReportType.FIND_BEST_BORROWED_BOOK.getValue());
        ReportDTO report = new ReportDTO();
        Map<String, Map<String, Integer>> mostBorrowedBookPerBranch = report.getMostBorrowedBookPerBranch();
        for (BookDTO book : books) {
            String inventoryName = book.getInventory().getName();
            String name = book.getName();
            int borrowedTotal = book.getBorrowedTotal();
            if(mostBorrowedBookPerBranch.containsKey(inventoryName)){
                mostBorrowedBookPerBranch.get(inventoryName).put(name,borrowedTotal);
            } else{
                Map<String, Integer> bookMap = new HashMap<>();
                bookMap.put(name, borrowedTotal);
                mostBorrowedBookPerBranch.put(book.getInventory().getName(), bookMap);
            }
            
            
        }
        return report;
    }

    @Override
    public ReportDTO getOverdueBooksPerInventory() throws UnmatchIDException {
        List<BookDTO> books = bookService.getBookReport(EReportType.FIND_OVERDUE_BOOK.getValue());
        ReportDTO report = new ReportDTO();
        Map<String, List<String>> overdueBookPerBranch = report.getOverdueBookPerBranch();
        for (BookDTO book : books) {
            String inventoryName = book.getInventory().getName();
            String name = book.getName();
            if(overdueBookPerBranch.containsKey(inventoryName)){
                overdueBookPerBranch.get(inventoryName).add(name);
            } else{
                List<String> bookList = new ArrayList<>();
                bookList.add(name);
                overdueBookPerBranch.put(book.getInventory().getName(), bookList);
            }           
        }
        return report;
    }
}
