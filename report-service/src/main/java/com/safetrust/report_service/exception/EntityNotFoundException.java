package com.safetrust.report_service.exception;

public class EntityNotFoundException extends Exception {
    /**
    * 
    * <p>Entity Not Found Exception constructor</p>
    * @param message exception message
    *
    */
    public EntityNotFoundException(String message){
        super(message);
    }
}
