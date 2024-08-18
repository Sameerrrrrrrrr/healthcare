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
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity<String> register(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("password") String password,
            @RequestParam("medLic") String medLic,
            @RequestParam("file") MultipartFile file) throws AccountAlreadyExistsException{
		doctorService.register(name,email,phoneNumber,password,medLic,file);
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
