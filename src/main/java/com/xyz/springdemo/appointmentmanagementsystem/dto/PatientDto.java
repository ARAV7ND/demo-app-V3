package com.xyz.springdemo.appointmentmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private int patientId;

    @NotNull(message = "first name Required")
    @Size(min = 3,message = "is required")
    @Pattern(regexp = "[^0-9]*",message = "numbers are not allowed")
    private String firstName;

    @NotNull(message = "last name Required")
    @Size(min = 3,message = "is required")
    @Pattern(regexp = "[^0-9]*",message = "numbers are not allowed")
    private String lastName;

    @Email
    private String email;

    @NotNull(message = "phone number is required")
    @Pattern(regexp = "[^a-zA-Z]*",message = "letters are not allowed")
    @Size(min = 6,max = 15)
    private String phone;
}
