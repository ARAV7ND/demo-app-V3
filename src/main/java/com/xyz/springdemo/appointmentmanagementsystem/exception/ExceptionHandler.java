package com.xyz.springdemo.appointmentmanagementsystem.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.text.ParseException;

@ControllerAdvice
public class ExceptionHandler {

    private static final String ERROR_PAGE = "error/error-page";
    private static final String MESSAGE = "message";

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String parseException(ParseException e,Model model){
        model.addAttribute(MESSAGE,e.getMessage());
        return ERROR_PAGE;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String patientNotFoundExceptionHandler(PatientNotFoundException e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        return ERROR_PAGE;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String doctorNotFoundExceptionHandler(DoctorNotFoundException e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        return ERROR_PAGE;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String exceptionHandler(MyRuntimeException e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        return ERROR_PAGE;
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    public String exceptionHandler(Exception e, Model model){
        model.addAttribute(MESSAGE,e.getMessage());
        return ERROR_PAGE;
    }


}
