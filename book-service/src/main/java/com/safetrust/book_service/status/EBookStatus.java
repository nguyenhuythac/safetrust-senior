package com.safetrust.book_service.status;

public enum EBookStatus {
    AVAILABLE("available"),
    BORROWING("borrowing"),
    OVERDUE("overdue"),
    LOST("lost");

    EBookStatus(String value){
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
