package com.safetrust.user_service.status;

public enum ETrackingUser {
    BORROWED("borrowed"),
    RETURNED("returned"),
    OVERDUED("overdued"),
    FINES("fines");

    private ETrackingUser(String value){
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
