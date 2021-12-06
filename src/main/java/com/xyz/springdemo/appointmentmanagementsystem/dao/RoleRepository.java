package com.xyz.springdemo.appointmentmanagementsystem.dao;

import com.xyz.springdemo.appointmentmanagementsystem.entity.Patient;
import com.xyz.springdemo.appointmentmanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query(value = "select * from roles where username = ?1",nativeQuery = true)
    Role findByEmail(String email);

    @Override
    Optional<Role> findById(Integer integer);
}
