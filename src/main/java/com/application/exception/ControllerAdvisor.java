package com.application.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoJobsListedException.class)
    public ResponseEntity<Object> handleNoJobsListedException(NoJobsListedException ex, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","No Jobs Found");
        body.put("status",HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(NoSuchJobException.class)
    public ResponseEntity<Object> handleNoSuchJobException(NoSuchJobException ex, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","No Job with this Job ID");
        body.put("status",HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CreateJobException.class)
    public ResponseEntity<Object> handleCreateJobException(CreateJobException ex, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage());
        body.put("status",HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ){
        Map<String, Object>  body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status",status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors",errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }
}
