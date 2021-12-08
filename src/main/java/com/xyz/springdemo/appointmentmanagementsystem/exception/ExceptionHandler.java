package com.xyz.springdemo.appointmentmanagementsystem.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.text.ParseException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String parseException(ParseException e,Model model){
        String message = "please enter the date and time in correct format";
        model.addAttribute("message",message);
        return "error/error-page";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String exceptionHandler(MyRuntimeException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error/error-page";
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    public String exceptionHandler(Exception e, Model model){
        model.addAttribute("message",e.getMessage());
        return "error/error-page";
    }


}
