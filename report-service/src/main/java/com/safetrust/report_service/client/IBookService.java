package com.safetrust.report_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.safetrust.report_service.exception.UnmatchIDException;
import com.safetrust.report_service.model.BookDTO;

@FeignClient(name = "book-service", url = "http://localhost:8082/book")
public interface IBookService {

    @GetMapping("/report/{report}")
    public List<BookDTO> getBookReport(@PathVariable("report") String report) throws UnmatchIDException;

    @GetMapping("/report/countbook")
    public int getBookReportAvaille() throws UnmatchIDException;
}
