package com.xyz.springdemo.appointmentmanagementsystem.dto;

import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotBlank(message = "date cannot be null")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$",message = "input should in dd/MM/yyyy format")
    private String date;

    @NotBlank(message = "startTime cannot be null")
    @Pattern(regexp = "^(0?[1-9]|1[0-2]):[0-5][0-9]$",message = "format is hh:mm")
    private String startTime;

    @NotBlank(message = "endTime cannot be null")
    @DateTimeFormat(pattern = "hh:mm")
    @Pattern(regexp = "^(0?[1-9]|1[0-2]):[0-5][0-9]$",message = "format is hh:mm")
    private String endTime;

    private Doctor doctor;

    private Patient patient;

}
