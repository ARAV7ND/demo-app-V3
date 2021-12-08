package com.xyz.springdemo.appointmentmanagementsystem.exception;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
