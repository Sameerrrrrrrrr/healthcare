package com.project.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.exceptions.AccountAlreadyExistsException;
import com.project.exceptions.InvalidPasswordException;
import com.project.exceptions.UserNotFoundException;
import com.project.model.Patient;
import com.project.repository.PatientRepo;

@Service
public class PatientService {
	private JavaMailSender mailSender;
	private  PasswordEncoder passwordEncoder;
	private PatientRepo patientRepo;
	public PatientService(PatientRepo patientRepo,PasswordEncoder passwordEncoder,JavaMailSender mailSender){
		this.passwordEncoder =passwordEncoder;
		this.patientRepo=patientRepo;
		this.mailSender=mailSender;
	}
	
	public String register(Patient patient) throws AccountAlreadyExistsException {
		Patient pat=patientRepo.findByEmail(patient.getEmail());
		if(pat!=null) {
			throw new AccountAlreadyExistsException("Account already exists with this email");
		}
		else {
			String encodedPassword=passwordEncoder.encode(patient.getPassword());
			patient.setPassword(encodedPassword);
			patientRepo.save(patient);
			sendEmail(patient.getEmail(), "Dear "+patient.getName()+",\n"+ "Thank you for the registration");
			return "Registration Successful";
		}
	}
	 
	public Patient login(String email,String password) throws UserNotFoundException, InvalidPasswordException {
		Patient pat=patientRepo.findByEmail(email);
		if(pat!=null) {
			if(passwordEncoder.matches(password, pat.getPassword())==true) {
				return pat;
			}
			else {
				throw new InvalidPasswordException("Enter correct password");
			}
		}
		else {
			throw new UserNotFoundException("User not found with "+email);
		}
	}
	
	public Patient updateDetails(Patient patient) {
		Patient pat=patientRepo.findByEmail(patient.getEmail());
		if(pat!=null) {
			pat.setName(patient.getName());
			pat.setNumber(patient.getNumber());
			String encodedPassword=passwordEncoder.encode(patient.getPassword());
			pat.setPassword(encodedPassword);
		}
		return patientRepo.save(pat);
	}
	
	public void sendEmail(String email ,String text) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("sameer997sa@gmail.com");
		message.setTo(email);
		message.setSubject("Healthcare Registration");
		message.setText(text);
		mailSender.send(message);
	}
	
}
