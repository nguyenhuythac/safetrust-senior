package com.safetrust.inventory_service.status;

public enum EReportType {
    FIND_BEST_BORROWED_BOOK("findbestbook"),
    FIND_OVERDUE_BOOK("findoverdue"),
    COUNT_AVAILABLE_BOOK("countbook");

    EReportType(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
