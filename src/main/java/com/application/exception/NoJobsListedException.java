package com.application.exception;

public class NoJobsListedException extends RuntimeException{
    public NoJobsListedException(){
        super("No Jobs Listed Currently");
    }
}
