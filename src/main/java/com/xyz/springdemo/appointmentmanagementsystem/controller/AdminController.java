package com.xyz.springdemo.appointmentmanagementsystem.controller;

import com.xyz.springdemo.appointmentmanagementsystem.converter.DoctorConverter;
import com.xyz.springdemo.appointmentmanagementsystem.converter.PatientConverter;
import com.xyz.springdemo.appointmentmanagementsystem.dto.DoctorDto;
import com.xyz.springdemo.appointmentmanagementsystem.dto.PatientDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.DoctorManager;
import com.xyz.springdemo.appointmentmanagementsystem.entity.PatientManager;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import com.xyz.springdemo.appointmentmanagementsystem.service.DoctorService;
import com.xyz.springdemo.appointmentmanagementsystem.service.PatientService;
import com.xyz.springdemo.appointmentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
@Controller
@RequestMapping("/admin")
public class AdminController {


    private final DoctorService doctorService;

    private final PatientService patientService;

    private final UserService userService;

    private final DoctorConverter doctorConverter;

    private final PatientConverter patientConverter;

    private String patientListControllerUrl = "redirect:/admin/patientList";
    private String doctorListControllerUrl = "redirect:/admin/doctorList";

    @Autowired
    public AdminController(DoctorService doctorService, PatientService patientService, UserService userService, DoctorConverter doctorConverter, PatientConverter patientConverter) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.userService = userService;
        this.doctorConverter = doctorConverter;
        this.patientConverter = patientConverter;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,trimmerEditor);
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("user",new Patient("Admin","site","123456"));
        return "admin/admin-home";
    }
    @GetMapping("/add")
    public String addDoctor(Model model){
        model.addAttribute("user",new DoctorManager());
        return "admin/doctor-registration";
    }

    @PostMapping("/saveDoctor")
    public String saveDoctor(@Valid @ModelAttribute("user") DoctorManager doctorDto, BindingResult bindingResult){
        if(userService.usernameAlreadyExists(doctorDto.getUsername())){
            bindingResult.addError(new FieldError("user","username","username already exists"));
        }
        if(bindingResult.hasErrors()){
            return "admin/doctor-registration";
        }
        doctorService.save(doctorDto);
        return doctorListControllerUrl;
    }

    @GetMapping("/doctorList")
        public String getDoctorsList(Model model){
            List<Doctor> doctorList = doctorService.findAll();
            model.addAttribute("users",doctorList);
            model.addAttribute("size",doctorList.size());
            return "admin/doctor-list";
        }

    @GetMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("id") int id){
        doctorService.deleteById(id);
        return doctorListControllerUrl;
    }

    @GetMapping("/updateDoctor")
    public String updateDoctor(@RequestParam("id") int id,Model model){
        Doctor doctor= doctorService.findById(id);
        DoctorDto doctorDto = doctorConverter.entityToDto(doctor);
        model.addAttribute("doctor",doctorDto);
        return "admin/doctor-update-form";
    }

    @PostMapping("/doctorMerge")
    public String saveDoctor(@Valid @ModelAttribute("doctor") DoctorDto doctorDto,BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "admin/doctor-update-form";
        }

        doctorService.saveNew(doctorDto);
        return doctorListControllerUrl;
    }

    @GetMapping("/patientList")
    public String patientList(Model model){
        List<Patient> patientList = patientService.findAll();
        model.addAttribute("users",patientList);
        model.addAttribute("size",patientList.size());
        return "admin/patient-list";
    }


    @GetMapping("/updatePatient")
    public String updatePatient(@RequestParam("id") int id,Model model){
        Patient patient = patientService.findById(id);
        PatientDto patientDto = patientConverter.entityToDto(patient);
        model.addAttribute("user",patientDto);
        return "admin/patient-update-form";
    }


    @PostMapping("/patientMerge")
    public String savePatient(@Valid @ModelAttribute("user") PatientDto patientDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/patient-update-form";
        }
        patientService.save(patientDto);
        return patientListControllerUrl;
    }
    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("user") PatientManager patientDto){
        patientService.save(patientDto);
        return patientListControllerUrl;
    }
    @GetMapping("/deletePatient")
    public String deletePatient(@RequestParam("id") int id){
            patientService.deleteById(id);
        return patientListControllerUrl;
    }
}
