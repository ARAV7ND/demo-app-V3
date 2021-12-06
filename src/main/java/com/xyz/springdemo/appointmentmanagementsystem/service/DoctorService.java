package com.xyz.springdemo.appointmentmanagementsystem.service;

import com.xyz.springdemo.appointmentmanagementsystem.dto.DoctorDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.DoctorManager;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;

import java.util.*;
public interface DoctorService {
    void save(DoctorManager doctorDto);
    void deleteById(Integer integer);
    void deleteByUsername(String username);
    List<Doctor> findAll();
    Doctor findById(int id);
    DoctorManager update(int id);
    void addAppointment(int doctorId, Appointment appointment);
    Doctor findByUsername(String username);
    List<Appointment> appointmentList(int id);
    void saveNew(DoctorDto doctorDto);
    Doctor findLoggedInUser();
}
