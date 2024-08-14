package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer>{
	Patient findByEmail(String email);
}
