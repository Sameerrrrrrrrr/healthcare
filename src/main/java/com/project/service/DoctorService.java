package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.exceptions.AccountAlreadyExistsException;
import com.project.exceptions.InvalidPasswordException;
import com.project.exceptions.UserNotFoundException;
import com.project.model.Doctor;
import com.project.model.Patient;
import com.project.repository.DoctorRepo;

@Service
public class DoctorService {
	
	private DoctorRepo doctorRepo;
	private  PasswordEncoder passwordEncoder;
	
	public DoctorService(DoctorRepo doctorRepo, PasswordEncoder passwordEncoder) {
		this.doctorRepo = doctorRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public Doctor register(Doctor doctor) throws AccountAlreadyExistsException {
		Doctor doc=doctorRepo.findByEmail(doctor.getEmail());
		if(doc!=null) {
			throw new AccountAlreadyExistsException("Account already exists with this email");
		}
		else {
			String encodedPassword=passwordEncoder.encode(doctor.getPassword());
			doctor.setPassword(encodedPassword);
			return doctorRepo.save(doctor);
		}
	}
	
	public Doctor login(String email,String password) throws UserNotFoundException, InvalidPasswordException {
		Doctor doc=doctorRepo.findByEmail(email);
		if(doc!=null) {
			if(passwordEncoder.matches(password, doc.getPassword())==true) {
				return doc;
			}
			else {
				throw new InvalidPasswordException("Enter correct password");
			}
		}
		else {
			throw new UserNotFoundException("User not found with "+ email);
		}
	}
	
	public Doctor updateDetails(Doctor doctor) {
		Doctor doc=doctorRepo.findByEmail(doctor.getEmail());
		if(doc!=null) {
			doc.setName(doctor.getName());
			doc.setNumber(doctor.getNumber());
			String encodedPassword=passwordEncoder.encode(doctor.getPassword());
			doc.setPassword(encodedPassword);
		}
		return doctorRepo.save(doc);
	}
	
	
}
