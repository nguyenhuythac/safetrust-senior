package com.safetrust.report_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetrust.report_service.exception.EntityNotFoundException;
import com.safetrust.report_service.exception.UnmatchIDException;
import com.safetrust.report_service.model.ReportDTO;
import com.safetrust.report_service.service.IReportService;

@RestController
@RequestMapping("/report")
public class ReportController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IReportService repostService;

    @GetMapping("/best-borrowed-book")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReportDTO> getBesBookPerInventory() throws EntityNotFoundException, UnmatchIDException {
        logger.info("Start report get all most borrowed book per inventory: ");
        ReportDTO report = repostService.getBesBookPerInventory();
        logger.info("End report get all most borrowed book per inventory: ");
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/overdue-books")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReportDTO> getOverdueBooksPerInventory() throws EntityNotFoundException, UnmatchIDException {
        logger.info("Start report get all overdued book per inventory: ");
        ReportDTO report = repostService.getOverdueBooksPerInventory();
        logger.info("End report get all overdued book per inventory: ");
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/count-available")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReportDTO> countAvailableBooksAndUserPerInventory()
            throws EntityNotFoundException, UnmatchIDException {
        logger.info("Start count Available Books And Users PerInventory: ");
        ReportDTO report = repostService.countAvailableBooksAndUserPerInventory();
        logger.info("End count Available Books And Users PerInventory: ");
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
