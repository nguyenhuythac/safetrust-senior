package com.safetrust.book_service.exception;

/**
* 
* This is Exception class that handles Unmatched ID 
* between url ID and body Id in HTTP post request.
* 
* @author Thac Nguyen
*/
public class UnmatchIDException extends Exception{
    /**
    * 
    * <p>Unmatched ID Exception constructor</p>
    * @param message exception message
    *
    */
    public UnmatchIDException(String message){
        super(message);
    }
}
