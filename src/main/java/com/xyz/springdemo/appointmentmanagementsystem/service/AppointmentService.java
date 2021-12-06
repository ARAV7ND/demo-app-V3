package com.xyz.springdemo.appointmentmanagementsystem.service;

import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;

import java.text.ParseException;
import java.util.List;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    boolean isSlotAvailable(Appointment appointment) throws ParseException;
    List<Appointment> findAllByDoctorId(int doctorId);
}
