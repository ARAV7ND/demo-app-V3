package com.xyz.springdemo.appointmentmanagementsystem.repository;

import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {
    @Override
    List<Patient> findAll();

    @Override
    void deleteById(Integer integer);

    @Query(value = "select * from patient where user_id = ?1",nativeQuery = true)
    Patient findByUserId(int id);




}
