package com.application.exception;

public class NoSuchJobException extends RuntimeException{
    public NoSuchJobException(Long id){
        super("No Such Job with Job ID: " + id);
    }
}
