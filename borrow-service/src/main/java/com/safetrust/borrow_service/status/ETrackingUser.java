package com.safetrust.borrow_service.status;

public enum ETrackingUser {
    BORROWED("borrowed"),
    RETURNED("returned"),
    OVERDUED("overdued"),
    FINES("fines");

    ETrackingUser(String value){
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
