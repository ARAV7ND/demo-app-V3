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
import java.security.Principal;
import java.util.List;
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorConverter doctorConverter;

    @Autowired
    private PatientConverter patientConverter;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,trimmerEditor);
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal){
        model.addAttribute("user",new Patient("Admin","site","admin@gmail.com","123456"));
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
//            throw new MyRuntimeException("User already Exists");
            bindingResult.addError(new FieldError("user","username","username already exists"));
        }
        if(bindingResult.hasErrors()){
            return "admin/doctor-registration";
        }
        doctorService.save(doctorDto);
        return "redirect:/admin/doctorList";
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
        return "redirect:/admin/doctorList";
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
        return "redirect:/admin/doctorList";
    }

    @GetMapping("/patientList")
    public String patientList(Model model){
        List<Patient> patientList = patientService.findAll();
        model.addAttribute("users",patientList);
        model.addAttribute("size",patientList.size());
        return "admin/patient-list";
    }

//    @GetMapping("/updatePatient")
//    public String updatePatient(@RequestParam("id") int id,Model model){
//        PatientManager patientManager = null;
//        patientManager = patientService.update(id);
//        model.addAttribute("user",patientManager);
//        return "admin/patient-registration";
//    }

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
        return "redirect:/admin/patientList";
    }
    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("user") PatientManager patientDto){
        patientService.save(patientDto);
        return "redirect:/admin/patientList";
    }
    @GetMapping("/deletePatient")
    public String deletePatient(@RequestParam("id") int id){
            patientService.deleteById(id);
        return "redirect:/admin/patientList";
    }
}
