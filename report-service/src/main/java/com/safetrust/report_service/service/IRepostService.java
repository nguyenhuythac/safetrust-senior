package com.safetrust.report_service.service;

import com.safetrust.report_service.exception.UnmatchIDException;
import com.safetrust.report_service.model.ReportDTO;

public interface IRepostService {

    ReportDTO getBesBookPerInventory() throws UnmatchIDException;

    ReportDTO getOverdueBooksPerInventory() throws UnmatchIDException;
    
}
