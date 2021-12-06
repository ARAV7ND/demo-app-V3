package com.xyz.springdemo.appointmentmanagementsystem.controller;
import com.xyz.springdemo.appointmentmanagementsystem.converter.PatientConverter;
import com.xyz.springdemo.appointmentmanagementsystem.dto.PatientDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import com.xyz.springdemo.appointmentmanagementsystem.service.AppointmentService;
import com.xyz.springdemo.appointmentmanagementsystem.service.DoctorService;
import com.xyz.springdemo.appointmentmanagementsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    private final DoctorService doctorService;

    private final AppointmentService appointmentService;

    private final PatientConverter patientConverter;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService, AppointmentService appointmentService, PatientConverter patientConverter) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.patientConverter = patientConverter;
    }

    @GetMapping("/home")
    public String home(Model model){
        Patient patient = patientService.loggedInUser();
        model.addAttribute("user",patient);
        return "patient/patient-home";
    }

    @GetMapping("/doctorList")
    public String getDoctorsList(Model model) {
        List<Doctor> doctorList = doctorService.findAll();
        model.addAttribute("size",doctorList.size());
        model.addAttribute("users", doctorList);
        return "patient/doctor-list";
    }

    @GetMapping("/appointmentForm")
    public String showAppointmentForm(@RequestParam("id") int id, Model model){
        Appointment appointment = new Appointment();
        appointment.setDoctorId(id);
        model.addAttribute("appointment",appointment);
        return "patient/appointment-form";
    }

    @PostMapping("/addAppointment")
    public String addAppointment(@ModelAttribute("appointment") Appointment appointment) throws ParseException {
        Patient patient = patientService.loggedInUser();
        int patientId = patient.getPatientId();
        appointment.setPatientId(patientId);
        patientService.addAppointment(patientId,appointment);
        doctorService.addAppointment(appointment.getDoctorId(),appointment);
        if(!appointmentService.isSlotAvailable(appointment)){
            throw new RuntimeException("Sorry slot is already booked..!");
        }
        appointmentService.save(appointment);
        return "redirect:/patient/home";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id,Model model){
        Patient patient = patientService.findById(id);
        PatientDto patientDto = patientConverter.entityToDto(patient);
        model.addAttribute("user",patientDto);
        return "patient/patient-update-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") PatientDto patientDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
           return "patient/patient-update-form";
        }
        patientService.save(patientDto);
        return "redirect:/patient/home";
    }
}


























