package com.xyz.springdemo.appointmentmanagementsystem.controller;

import com.xyz.springdemo.appointmentmanagementsystem.converter.DoctorConverter;
import com.xyz.springdemo.appointmentmanagementsystem.dto.DoctorDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.*;
import com.xyz.springdemo.appointmentmanagementsystem.service.DoctorService;
import com.xyz.springdemo.appointmentmanagementsystem.service.PatientService;
import com.xyz.springdemo.appointmentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorConverter doctorConverter;

    @GetMapping("/home")
    public String home(Model model){
        Doctor doctor = doctorService.findLoggedInUser();
        model.addAttribute("user",doctor);
        return "doctor/doctor-home";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id,Model model){
        Doctor doctor= doctorService.findById(id);
        DoctorDto doctorDto = doctorConverter.entityToDto(doctor);
        model.addAttribute("user",doctorDto);
        return "doctor/doctor-update-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") DoctorDto doctorDto){
        doctorService.saveNew(doctorDto);
        return "redirect:/doctor/home";
    }

    @GetMapping("/appointmentList")
    public String appointmentList(@RequestParam("id") int id,Model model){
        List<Appointment> appointmentList = doctorService.appointmentList(id);
        model.addAttribute("appointmentList",appointmentList);
        return "doctor/list";
    }
}
