package com.xyz.springdemo.appointmentmanagementsystem.exception;

public class AppointmentNotPossibleException extends RuntimeException{
    public AppointmentNotPossibleException(String message) {
        super(message);
    }
}
