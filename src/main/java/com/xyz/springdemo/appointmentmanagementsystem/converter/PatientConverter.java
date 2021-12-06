package com.xyz.springdemo.appointmentmanagementsystem.converter;

import com.xyz.springdemo.appointmentmanagementsystem.dto.PatientDto;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class PatientConverter {
    public PatientDto entityToDto(Patient patient){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(patient,PatientDto.class);
    }
    public Patient dtoToEntity(PatientDto patientDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(patientDto,Patient.class);
    }
}
