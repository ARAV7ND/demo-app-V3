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
import com.xyz.springdemo.appointmentmanagementsystem.exception.DoctorNotFoundException;
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

    private final DoctorRepository doctorRepository;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AppointmentRepository appointmentRepository;

    private final DoctorConverter doctorConverter;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, UserService userService,
                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                     AppointmentRepository appointmentRepository,
                                     DoctorConverter doctorConverter) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appointmentRepository = appointmentRepository;
        this.doctorConverter = doctorConverter;
    }

    @Override
    @Transactional
    public void save(DoctorManager doctorManager) {
            Doctor doctor = new Doctor(doctorManager.getFirstName(),doctorManager.getLastName(),doctorManager.getSpecialist(),doctorManager.getAddress());
            User user = new User(doctorManager.getUsername(),bCryptPasswordEncoder.encode(doctorManager.getPassword()),Arrays.asList(new Role("ROLE_DOCTOR")));
            doctor.setUser(user);
            doctorRepository.save(doctor);
            userService.save(user);
    }

    @Override
    public void deleteById(Integer integer) {
        Doctor doctor = findById(integer);

        if(!doctor.getAppointments().isEmpty()){
            throw new UnsupportedOperationException("can't delete doctor when there is an appointment");
        }
        User user = userService.findById(doctor.getUser().getId());
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
            throw new DoctorNotFoundException("Doctor not found with id "+id);
        }
        return doctor;
    }

    @Override
    @Transactional
    public DoctorManager update(int id) {
        Doctor doctor = findById(id);
        User user = userService.findById(doctor.getUser().getId());
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

    @Override
    @Transactional
    public void addAppointment(Appointment appointment) {
        Doctor doctor = findById(appointment.getDoctor().getId());
        doctor.addAppointment(appointment);
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor findByUsername(String username) {
        User user = userService.findByUsername(username);
        return doctorRepository.findByUserId(user.getId());
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
        String username = ((UserDetails)obj).getUsername();
        User user = userService.findByUsername(username);
        return doctorRepository.findByUserId(user.getId());
    }

}
