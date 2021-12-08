package com.xyz.springdemo.appointmentmanagementsystem.service;

import com.xyz.springdemo.appointmentmanagementsystem.dao.AppointmentRepository;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private AppointmentRepository appointmentRepository;


    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment save(Appointment appointment) {

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAllByDoctorId(int doctorId) {
        return appointmentRepository.findAllByDoctorId(doctorId);
    }

    @Override
    public boolean isSlotAvailable(Appointment appointment) throws ParseException {
        List<Appointment> appointments = findAllByDoctorId(appointment.getDoctor().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            for (Appointment appointmentObj : appointments) {
                String tempDate = appointmentObj.getDate() + " " + appointmentObj.getEndTime();
                Date before = sdf.parse(tempDate);
                Date after = sdf.parse(appointment.getDate() + " " + appointment.getEndTime());
                if (before.getTime() > after.getTime()) {
                    return false;
                }
            }
        return true;
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        appointmentRepository.deleteById(integer);
    }
}
