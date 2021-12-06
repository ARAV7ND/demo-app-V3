package com.xyz.springdemo.appointmentmanagementsystem.service;

import com.xyz.springdemo.appointmentmanagementsystem.converter.PatientConverter;
import com.xyz.springdemo.appointmentmanagementsystem.dao.PatientRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dto.PatientDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.PatientManager;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Role;
import com.xyz.springdemo.appointmentmanagementsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PatientConverter patientConverter;

    public Patient loggedInUser() {
        Object obj = userService.findLoggedInUserDetails();
        String username = ((UserDetails)obj).getUsername();
        Patient patient = findByUsername(username);
        return patient;
    }

    @Override
    public void save(PatientManager patientDto) {
        Patient patient = new Patient(patientDto.getFirstName(),patientDto.getLastName(),patientDto.getUsername(),patientDto.getPhone());
        patient.setPatientId(patientDto.getId());
        User user = new User(patientDto.getUsername(), bCryptPasswordEncoder.encode(patientDto.getPassword()),Arrays.asList(new Role("ROLE_USER", patientDto.getUsername())));
        user.setId(patientDto.getTemp());
        userService.save(user);
        patientRepository.save(patient);

    }

    @Override
    public void deleteById(Integer id) {
        Patient patient = findById(id);
        if(patient.getAppointments().size()>0){
            throw new RuntimeException("Can't delete when there is an active appointment booked");
        }
        User user = userService.findByUsername(patient.getEmail());
        userService.deleteById(user.getId());
        patientRepository.deleteById(id);

    }

    @Override
    public Patient findById(int id) {
        Optional<Patient> result = patientRepository.findById(id);
        Patient patient = null ;
        if(result.isPresent()){
            patient = result.get();
        }else{
            throw new RuntimeException("Patient not found with id "+id);
        }
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public PatientManager update(int id) {
        Patient patient = findById(id);
        User user = userService.findByUsername(patient.getEmail());
        PatientManager patientManager = new PatientManager(patient.getFirstName(),patient.getLastName(),
                                     patient.getPhone(),user.getUsername(), user.getPassword());
            patientManager.setId(patient.getPatientId());
        patientManager.setTemp(user.getId());
        return patientManager;
    }

    @Override
    public Patient findByUsername(String username) {
        return patientRepository.findByEmail(username);
    }

    @Override
    public void addAppointment(int patientId,Appointment appointment) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        Patient patientObj = null;
        if(patient.isPresent()){
            patientObj = patient.get();
            patientObj.addAppointment(appointment);
        }
    }
    @Override
    public void save(PatientDto patientDto){
        Patient patient = patientConverter.dtoToEntity(patientDto);
        patientRepository.save(patient);
    }



}
