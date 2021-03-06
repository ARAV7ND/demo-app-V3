package com.xyz.springdemo.appointmentmanagementsystem.repository;

import com.xyz.springdemo.appointmentmanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Override
    Optional<Role> findById(Integer integer);
}
