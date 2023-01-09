package com.application.exception;

public class UpdateJobException extends RuntimeException{
    public UpdateJobException(){
        super("Error in job updating process please try again");
    }
}
