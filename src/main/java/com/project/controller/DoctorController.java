package com.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.exceptions.AccountAlreadyExistsException;
import com.project.exceptions.InvalidPasswordException;
import com.project.exceptions.UserNotFoundException;
import com.project.model.Doctor;
import com.project.service.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
	
	private final DoctorService doctorService;

	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Doctor doctor) throws AccountAlreadyExistsException{
		doctorService.register(doctor);
		return new ResponseEntity<String>("Registered Successfully",HttpStatus.CREATED);
	}
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email,@RequestParam String password) throws UserNotFoundException, InvalidPasswordException{
		Doctor doc=doctorService.login(email, password);
		return new ResponseEntity<String>("Welcome "+doc.getName(),HttpStatus.OK);
	}
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Doctor doctor){
		doctorService.updateDetails(doctor);
		return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
	}
	
	
}
