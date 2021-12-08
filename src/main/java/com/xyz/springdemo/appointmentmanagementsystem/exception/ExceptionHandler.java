package com.xyz.springdemo.appointmentmanagementsystem.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.text.ParseException;

@ControllerAdvice
public class ExceptionHandler {

    private final static  String urlForError = "error/error-page";
    private final static String message = "message";
    @org.springframework.web.bind.annotation.ExceptionHandler
    public String parseException(ParseException e,Model model){
        String msg = "please enter the date and time in correct format";
        model.addAttribute(message,msg);
        return urlForError;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String patientNotFoundExceptionHandler(PatientNotFoundException e, Model model) {
        model.addAttribute(message, e.getMessage());
        return urlForError;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String doctorNotFoundExceptionHandler(DoctorNotFoundException e, Model model) {
        model.addAttribute(message, e.getMessage());
        return urlForError;
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    public String exceptionHandler(MyRuntimeException e, Model model) {
        model.addAttribute(message, e.getMessage());
        return urlForError;
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    public String exceptionHandler(Exception e, Model model){
        model.addAttribute(message,e.getMessage());
        return urlForError;
    }


}
