package com.xyz.springdemo.appointmentmanagementsystem.converter;

import com.xyz.springdemo.appointmentmanagementsystem.dto.DoctorDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class DoctorConverter {

    public DoctorDto entityToDto(Doctor doctor){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(doctor,DoctorDto.class);
    }

    public Doctor dtoToEntity(DoctorDto doctorDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(doctorDto,Doctor.class);
    }
}
