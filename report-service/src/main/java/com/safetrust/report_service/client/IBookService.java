package com.safetrust.report_service.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.safetrust.report_service.exception.UnmatchIDException;
import com.safetrust.report_service.model.BookDTO;

@FeignClient(name = "book-service", url = "http://localhost:8082/book")
public interface IBookService {

    @GetMapping("/report/{report}")
    List<BookDTO> getBookReport(@PathVariable("report") String report) throws UnmatchIDException;

    @GetMapping("/report/countbook")
    Map<String, Long> getBookReportAvaille() throws UnmatchIDException;
}
