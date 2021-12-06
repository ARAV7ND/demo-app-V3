package com.xyz.springdemo.appointmentmanagementsystem.service;

import com.xyz.springdemo.appointmentmanagementsystem.converter.DoctorConverter;
import com.xyz.springdemo.appointmentmanagementsystem.dao.AppointmentRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dao.DoctorRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dto.DoctorDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.DoctorManager;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Role;
import com.xyz.springdemo.appointmentmanagementsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorConverter doctorConverter;

    @Override
    @Transactional
    public void save(DoctorManager doctorManager) {
        Doctor doctor = new Doctor(doctorManager.getFirstName(),doctorManager.getLastName(),doctorManager.getUsername(),doctorManager.getSpecialist(),doctorManager.getAddress());
        doctor.setId(doctorManager.getId());
        User user = new User(doctorManager.getUsername(), bCryptPasswordEncoder.encode(doctorManager.getPassword()), Arrays.asList(new Role("ROLE_DOCTOR",doctorManager.getUsername())));
        user.setId(doctorManager.getTemp());
        userService.save(user);
        doctorRepository.save(doctor);
    }



    @Override
    public void deleteById(Integer integer) {
        Doctor doctor = findById(integer);

        if(doctor.getAppointments().size()>0){
            throw new RuntimeException("can't delete doctor when there is an appointment");
        }
        User user = userService.findByUsername(doctor.getEmail());
        userService.deleteById(user.getId());
        doctorRepository.deleteById(doctor.getId());
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor findById(int id) {
        Optional<Doctor> result = doctorRepository.findById(id);
        Doctor doctor=null;
        if(result.isPresent()){
            doctor = result.get();
        }else{
            throw new RuntimeException("Doctor not found with id "+id);
        }
        return doctor;
    }

    @Override
    @Transactional
    public DoctorManager update(int id) {
        Doctor doctor = findById(id);
        User user = userService.findByUsername(doctor.getEmail());
        DoctorManager doctorManager = new DoctorManager(doctor.getFirstName(),doctor.getLastName(),
                                            user.getUsername(),user.getPassword(),doctor.getAddress(),doctor.getSpecialist());
        doctorManager.setId(doctor.getId());
        doctorManager.setTemp(user.getId());
        return doctorManager;
    }

    public void saveNew(DoctorDto doctorDto){
        Doctor doctor = doctorConverter.dtoToEntity(doctorDto);
        doctorRepository.save(doctor);
    }

    public DoctorDto updateNew(int id){
        Doctor doctor = findById(id);
        return doctorConverter.entityToDto(doctor);
    }

    @Override
    @Transactional
    public void addAppointment(int doctorId, Appointment appointment) {
        Optional<Doctor> result = doctorRepository.findById(doctorId);
        Doctor doctor=null;
        if(result.isPresent()){
            doctor = result.get();
        }else{
            throw new RuntimeException("404 no doctor found with id "+doctorId);
        }
        doctor.addAppointment(appointment);
    }

    @Override
    public Doctor findByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }

    @Override
    public List<Appointment> appointmentList(int id) {
        return appointmentRepository.findAllByDoctorId(id);
    }

    @Override
    public void deleteByUsername(String username) {
            userService.deleteByUsername(username);
    }

    @Override
    public Doctor findLoggedInUser() {
        Object obj = userService.findLoggedInUserDetails();
        Doctor doctor = findByUsername(((UserDetails)obj).getUsername());
        return doctor;
    }
}
