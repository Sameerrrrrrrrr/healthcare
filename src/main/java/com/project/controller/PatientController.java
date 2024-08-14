package com.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.project.model.Patient;
import com.project.repository.PatientRepo;
import com.project.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	private final PatientService patientService;
	
	public PatientController(PatientService patientService,PatientRepo patientRepo) {
		this.patientService=patientService;
	}
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Patient patient) throws AccountAlreadyExistsException{
		patientService.register(patient);
		return new ResponseEntity<String>("Registered Successfully",HttpStatus.CREATED);
	}
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email,@RequestParam String password) throws UserNotFoundException, InvalidPasswordException{
		Patient pat=patientService.login(email, password);
		return new ResponseEntity<String>("Welcome "+pat.getName(),HttpStatus.OK);
	}
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Patient patient){
		patientService.updateDetails(patient);
		return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
	}
	
	
	
}
