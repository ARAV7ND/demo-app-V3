package com.xyz.springdemo.appointmentmanagementsystem.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "date")
    @NotBlank(message = "date cannot be null")
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$",message = "input should in dd/MM/yyyy format")
    private String date;

    @NotBlank(message = "startTime cannot be null")
    @Pattern(regexp = "^(0?[1-9]|1[0-2]):[0-5][0-9]$",message = "format is hh:mm")
    @Column(name = "appointment_start_time")
    private String startTime;

    @NotBlank(message = "endTime cannot be null")
    @DateTimeFormat(pattern = "hh:mm")
    @Pattern(regexp = "^(0?[1-9]|1[0-2]):[0-5][0-9]$",message = "format is hh:mm")
    @Column(name = "appointment_end_time")
    private String endTime;

    @Column(name = "patient_id")
    private int patientId;
    @Column(name = "doctor_Id")
    private int doctorId;


    @ManyToOne
    @JoinColumn(name = "patient_id",insertable = false,updatable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id",insertable = false,updatable = false)
    private Doctor doctor;

    public Appointment() {
    }

    public Appointment(String date, String startTime, String endTime, int patientId, int doctorId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
