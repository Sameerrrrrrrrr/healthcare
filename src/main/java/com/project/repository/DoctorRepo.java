package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer>{
	Doctor findByEmail(String email);
}
