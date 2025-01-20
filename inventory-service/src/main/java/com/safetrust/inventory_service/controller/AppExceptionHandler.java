package com.safetrust.inventory_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.safetrust.inventory_service.exception.CanNotDeleteEntityException;
import com.safetrust.inventory_service.exception.EntityNotFoundException;
import com.safetrust.inventory_service.exception.UnmatchIDException;


/**
* 
* This class is used for unchecked exception handling.
* 
* Validation check.
* 
* @author Thac Nguyen
*/
@RestControllerAdvice
public class AppExceptionHandler {

    /**
    * 
    * <p>Handle Invalid created and updated Argument Exception</p>
    * @param MethodArgumentNotValidException Method Argument Not Valid Exception
    * @return errorMap Key is invalid field, value is error message
    *
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    /**
    * 
    * <p>Handle created and updated user not found Exception</p>
    * @param EntityNotFoundException Entity Not Found Exception
    * @return errorMap Key is invalid field, value is error message
    *
    */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleUserNotFound(EntityNotFoundException ex){
        return mapErrorMessage(ex.getMessage());
    }

    /**
    * 
    * <p>Handle created and updated user not found Exception</p>
    * @param UnmatchIDException Unmatched ID Exception
    * @return errorMap Key is invalid field, value is error message
    *
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnmatchIDException.class)
    public Map<String, String> handleUserNotFound(UnmatchIDException ex){
        return mapErrorMessage(ex.getMessage());
    }

    /**
    * 
    * <p>Handle created and updated user not found Exception</p>
    * @param UnmatchIDException Unmatched ID Exception
    * @return errorMap Key is invalid field, value is error message
    *
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CanNotDeleteEntityException.class)
    public Map<String, String> CannotDeleteEntityHandler(CanNotDeleteEntityException ex){
        return mapErrorMessage(ex.getMessage());
    }

    /**
    * 
    * <p>Put to map Error Message</p>
    * @param message The exception message
    * @return errorMap Key is invalid field, value is error message
    *
    */
    private Map<String, String> mapErrorMessage(String message){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", message);
        return errorMap;
    }
}
