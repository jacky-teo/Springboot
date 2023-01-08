package com.application.exception;

import com.application.model.Job;

public class CreateJobException extends RuntimeException{
    public CreateJobException (String msg){
        super(msg);
    }
}
