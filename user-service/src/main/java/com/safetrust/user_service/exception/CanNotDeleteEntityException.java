package com.safetrust.user_service.exception;

public class CanNotDeleteEntityException extends Exception {
    /**
    * 
    * <p>Entity Not Found Exception constructor</p>
    * @param message exception message
    *
    */
    public CanNotDeleteEntityException(String message){
        super(message);
    }
}
