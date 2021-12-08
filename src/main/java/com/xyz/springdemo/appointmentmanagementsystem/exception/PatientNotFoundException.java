package com.xyz.springdemo.appointmentmanagementsystem.exception;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String message){
        super(message);
    }
}
