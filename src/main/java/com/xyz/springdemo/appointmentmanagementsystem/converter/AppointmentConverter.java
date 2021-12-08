package com.xyz.springdemo.appointmentmanagementsystem.converter;

import com.xyz.springdemo.appointmentmanagementsystem.dto.AppointmentDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Appointment;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class AppointmentConverter {

    public AppointmentDto entityToDto(Appointment appointment){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(appointment,AppointmentDto.class);
    }

    public Appointment dtoToEntity(AppointmentDto appointmentDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(appointmentDto,Appointment.class);
    }
}
