package com.xyz.springdemo.appointmentmanagementsystem;

import com.xyz.springdemo.appointmentmanagementsystem.dao.DoctorRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dao.PatientRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dao.RoleRepository;
import com.xyz.springdemo.appointmentmanagementsystem.dao.UserRepository;
import com.xyz.springdemo.appointmentmanagementsystem.entity.*;
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

	private final PatientService patientService;

	private final PatientRepository patientRepository;

	private final AppointmentService appointmentService;

	private final DoctorRepository doctorRepository;

	private final DoctorService doctorService;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	@Autowired
	public AppointmentManagementSystemApplicationTests(PatientService patientService, PatientRepository patientRepository,
													   AppointmentService appointmentService, DoctorRepository doctorRepository,
													   DoctorService doctorService, UserRepository userRepository, RoleRepository roleRepository) {
		this.patientService = patientService;
		this.patientRepository = patientRepository;
		this.appointmentService = appointmentService;
		this.doctorRepository = doctorRepository;
		this.doctorService = doctorService;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Test
	@Order(1)
	 void savePatient(){
		PatientManager patient = new PatientManager("zakir","khan","456789123","zakir@gmail.com","test123");
		patientService.save(patient);
		Assertions.assertThat(patientService.findByUsername(patient.getUsername()).getPatientId()).isPositive();
	}

	@Test
	@Order(2)
	void findAllPatients(){
		List<Patient> patientList =  patientService.findAll();
		Assertions.assertThat(patientList.size()).isPositive();
	}

	@Test
	@Order(3)
	void findPatientById(){
		int patientId = patientService.findByUsername("zakir@gmail.com").getPatientId();
		Patient patient = patientService.findById(patientId);
		Assertions.assertThat(patient.getPatientId()).isEqualTo(patient.getPatientId());
	}


	@Test
	@Order(5)
	void saveDoctor(){
		DoctorManager doctor = new DoctorManager("priya","singh","priya@gmail.com","test123","mumbai","dermatology");
		doctorService.save(doctor);
		Assertions.assertThat(doctorService.findByUsername(doctor.getUsername()).getId()).isPositive();
	}

	@Test
	@Order(6)
	void findAllDoctor(){
		List<Doctor> doctorList = doctorService.findAll();
		Assertions.assertThat(doctorList.size()).isPositive();
	}

	@Test
	@Order(7)
	void findDoctorById(){
		int doctorId = doctorService.findByUsername("priya@gmail.com").getId();
		Doctor doctor = doctorService.findById(doctorId);
		Assertions.assertThat(doctor.getId()).isEqualTo(doctorId);
	}


	@Test
	@Order(9)
	void saveAnAppointment(){
		Doctor doctor = doctorService.findByUsername("priya@gmail.com");
		Patient patient = patientService.findByUsername("zakir@gmail.com");
		Appointment appointment = new Appointment("28/01/2020","12:00","01:30",doctor.getId(),patient.getPatientId());
		appointmentService.save(appointment);
		Assertions.assertThat(appointmentService.findAllByDoctorId(doctor.getId())).isNotNull();
	}

	@Test
	@Order(10)
	void findAllAppointments(){
		List<Appointment> appointmentList =  appointmentService.findAll();
		Assertions.assertThat(appointmentList.size()).isPositive();
	}

	@Test
	@Order(11)
	void findAllByDoctorId(){
		int doctorId = doctorService.findByUsername("priya@gmail.com").getId();
		List<Appointment> appointmentList = appointmentService.findAllByDoctorId(doctorId);
		Assertions.assertThat(appointmentList.size()).isPositive();
	}

	@Test
	@Order(12)
	void deleteDoctorById(){
		Doctor doctor = doctorService.findByUsername("priya@gmail.com");
		doctorRepository.deleteById(doctor.getId());
		userRepository.deleteByUsername(doctor.getEmail());
		Role role = roleRepository.findByEmail(doctor.getEmail());
		roleRepository.deleteById(role.getId());
		Doctor doctor1 = null;
		Optional<Doctor> result = doctorRepository.findById(doctor.getId());
		if(result.isPresent()){
			doctor1 = result.get();
		}
		Assertions.assertThat(doctor1).isNull();
	}

	@Test
	@Order(13)
	void deletePatientById(){
		Patient patient = patientService.findByUsername("zakir@gmail.com");
		patientRepository.deleteById(patient.getPatientId());
		userRepository.deleteByUsername(patient.getEmail());
		Role role = roleRepository.findByEmail(patient.getEmail());
		roleRepository.deleteById(role.getId());
		Patient patient1 = null;
		Optional<Patient> patientOptional = patientRepository.findById(patient.getPatientId());
		if(patientOptional.isPresent()){
			patient1 = patientOptional.get();
		}
		Assertions.assertThat(patient1).isNull();
	}


}
