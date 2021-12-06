package com.xyz.springdemo.appointmentmanagementsystem;

import com.xyz.springdemo.appointmentmanagementsystem.dao.DoctorRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dao.PatientRepository;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Doctor;
import com.xyz.springdemo.appointmentmanagementsystem.entity.DoctorManager;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import com.xyz.springdemo.appointmentmanagementsystem.entity.PatientManager;
import com.xyz.springdemo.appointmentmanagementsystem.service.AppointmentService;
import com.xyz.springdemo.appointmentmanagementsystem.service.DoctorService;
import com.xyz.springdemo.appointmentmanagementsystem.service.PatientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppointmentManagementSystemApplicationTests {

	@Autowired
	private PatientService patientService;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DoctorService doctorService;


	@Test
	@Order(1)
	public void savePatient(){
		PatientManager patient = new PatientManager("zakir","khan","456789123","zakir@gmail.com","test123");
		patientService.save(patient);
		Assertions.assertThat(patientService.findByUsername(patient.getUsername()).getPatientId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void findAllPatients(){
		List<Patient> patientList =  patientService.findAll();
		Assertions.assertThat(patientList.size()).isGreaterThan(0);
	}

	@Test
	@Order(3)
	public void findPatientById(){
		int patientId = patientService.findByUsername("zakir@gmail.com").getPatientId();
		Patient patient = patientService.findById(patientId);
		Assertions.assertThat(patient.getPatientId()).isEqualTo(patient.getPatientId());
	}

	@Test
	@Order(4)
	public void deletePatientById(){
		Patient patient = patientService.findByUsername("zakir@gmail.com");
		patientService.deleteById(patient.getPatientId());
		Patient patient1 = null;
		Optional<Patient> patientOptional = patientRepository.findById(patient.getPatientId());
		if(patientOptional.isPresent()){
			patient1 = patientOptional.get();
		}
		Assertions.assertThat(patient1).isNull();
	}

	@Test
	@Order(5)
	public void saveDoctor(){
		DoctorManager doctor = new DoctorManager("priya","singh","priya@gmail.com","test123","mumbai","dermatology");
		doctorService.save(doctor);
		Assertions.assertThat(doctorService.findByUsername(doctor.getUsername()).getId()).isGreaterThan(0);
	}

	@Test
	@Order(6)
	public void findAllDoctor(){
		List<Doctor> doctorList = doctorService.findAll();
		Assertions.assertThat(doctorList.size()).isGreaterThan(0);
	}

	@Test
	@Order(7)
	public void findDoctorById(){
		int doctorId = doctorService.findByUsername("priya@gmail.com").getId();
		Doctor doctor = doctorService.findById(doctorId);
		Assertions.assertThat(doctor.getId()).isEqualTo(doctorId);
	}

	@Test
	@Order(8)
	public void deleteDoctorById(){
		Doctor doctor = doctorService.findByUsername("priya@gmail.com");
		doctorService.deleteById(doctor.getId());
		Doctor doctor1 = null;
		Optional<Doctor> result = doctorRepository.findById(doctor.getId());
		if(result.isPresent()){
			doctor1 = result.get();
		}
		Assertions.assertThat(doctor1).isNull();

	}

}
