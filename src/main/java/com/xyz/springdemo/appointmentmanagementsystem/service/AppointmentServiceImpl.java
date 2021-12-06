package com.xyz.springdemo.appointmentmanagementsystem.service;

import com.xyz.springdemo.appointmentmanagementsystem.dao.AppointmentRepository;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    //constr

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
        List<Appointment> appointments = findAllByDoctorId(appointment.getDoctorId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        for(Appointment appointmentObj : appointments){
            String tempDate = appointmentObj.getDate()+" "+appointmentObj.getEndTime();
            Date before =  sdf.parse(tempDate);
            Date after = sdf.parse(appointment.getDate()+" "+appointment.getEndTime());
            return before.getTime() >  after.getTime();
        }
        return true;
    }
}
