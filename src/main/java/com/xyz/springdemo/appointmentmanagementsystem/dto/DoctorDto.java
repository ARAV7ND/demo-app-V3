package com.xyz.springdemo.appointmentmanagementsystem.dto;

import com.xyz.springdemo.appointmentmanagementsystem.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private int doctorId;

    @NotNull(message = "first name Required")
    @Size(min = 3,message = "is required")
    @Pattern(regexp = "[^0-9]*",message = "numbers are not allowed")
    private String firstName;

    @NotNull(message = "last name Required")
    @Size(min = 3,message = "is required")
    @Pattern(regexp = "[^0-9]*",message = "numbers are not allowed")
    private String lastName;

    @NotNull(message = "Specialist Required")
    @Size(min = 5,message = "is required")
    @Pattern(regexp = "[^0-9]*",message = "numbers are not allowed")
    private String specialist;

    @NotNull(message = "Address Required")
    @Size(min = 3,message = "is required")
    @Pattern(regexp = "[^0-9]*",message = "numbers are not allowed")
    private String address;

    private User user;
}
