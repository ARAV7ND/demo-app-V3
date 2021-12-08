package com.xyz.springdemo.appointmentmanagementsystem.controller;
import com.xyz.springdemo.appointmentmanagementsystem.converter.AppointmentConverter;
import com.xyz.springdemo.appointmentmanagementsystem.converter.PatientConverter;
import com.xyz.springdemo.appointmentmanagementsystem.dao.DoctorRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dao.PatientRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dto.AppointmentDto;
import com.xyz.springdemo.appointmentmanagementsystem.dto.PatientDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import com.xyz.springdemo.appointmentmanagementsystem.exception.AppointmentNotPossibleException;
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
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentConverter appointmentConverter;

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
        AppointmentDto appointmentDto = new AppointmentDto();
        Doctor doctor = doctorService.findById(id);
        Patient patient = patientService.loggedInUser();
        appointmentDto.setPatient(patient);
        appointmentDto.setDoctor(doctor);
        model.addAttribute("appointment",appointmentDto);
        return "patient/appointment-form";
    }

    @PostMapping("/addAppointment")
    public String addAppointment(@Valid @ModelAttribute("appointment") AppointmentDto appointmentDto,BindingResult bindingResult) throws ParseException {
        if(bindingResult.hasErrors()){
            return "patient/appointment-form";
        }
        Appointment appointment = appointmentConverter.dtoToEntity(appointmentDto);
        if(!appointmentService.isSlotAvailable(appointment)){
            throw new AppointmentNotPossibleException("Sorry slot is already booked..!");
        }
        doctorService.addAppointment(appointment);
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


























