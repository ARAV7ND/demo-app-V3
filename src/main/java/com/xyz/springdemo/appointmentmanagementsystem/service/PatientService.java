package com.xyz.springdemo.appointmentmanagementsystem.service;

import com.xyz.springdemo.appointmentmanagementsystem.dto.PatientDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.PatientManager;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import java.util.*;
public interface PatientService {
    void save(PatientManager patient);
    void deleteById(Integer integer);
    Patient findById(int id);
    List<Patient> findAll();
    PatientManager update(int id);
    Patient findByUsername(String username);
    void addAppointment(Appointment appointment);
    Patient loggedInUser();
    void save(PatientDto patientDto);

}
