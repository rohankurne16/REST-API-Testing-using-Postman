package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bin.Customer;
import com.example.demo.bin.Login;
import com.example.demo.repo.Customer_Repo;

import jakarta.transaction.Transactional;

@RestController
public class Customer_Controller {
	
	@Autowired
	Customer_Repo crr;
	
	
	@PostMapping("/customer")
	public ResponseEntity<?> addnewuser(@RequestBody Customer c) {
		
	Customer email = 	crr.findByEmail(c.getEmail());
	
		if(email != null) {
			return ResponseEntity.badRequest().body("Email already registered! plz login....");
		}
		
		crr.save(c);
				return ResponseEntity.accepted().body("Registered successfully");		
		}

	

	@PostMapping("/login_customer")
	public ResponseEntity<?> logincustomer(@RequestBody Login l){
		
		Customer check = crr.findByEmailAndPassword(l.getEmail(), l.getPass());
		
		if(check != null) {
			return ResponseEntity.accepted().body("Logged In Successfully");
		}
		
		return ResponseEntity.badRequest().body("Invalid Credentials");
		
	}
	
	
	@DeleteMapping("/customer/{email}")
	public ResponseEntity<?> deletecustomer(@PathVariable String email){
		
		Customer check = crr.findByEmail(email);
		
		if(check != null) {
			
			crr.deleteByEmail(email);
			return ResponseEntity.accepted().body("Customer Deleted Successfully");
		}
		else {
			return ResponseEntity.badRequest().body("Email not registered");
		}
			
	}
	
	@GetMapping("/customer")
	public ResponseEntity<?> showallcustomer(){
		Iterable<Customer> allcustomers =  crr.findAll();
		
		if(allcustomers != null) {
			return ResponseEntity.accepted().body(allcustomers);
		}
		return ResponseEntity.badRequest().body("No Customer Found");
		
	}
	
	@GetMapping("/customer/{email}")
	public ResponseEntity<?> showcustomer(@PathVariable String email){
		
		Customer user = crr.findByEmail(email);
		if(user != null) {
			return ResponseEntity.accepted().body(user);
		}
		return ResponseEntity.badRequest().body("Email not registered");
	}
	
	
	@PutMapping("/customer/{email}")
	public ResponseEntity<?> updatecustomer(@PathVariable String email, @RequestBody Customer new_data){
		
		Customer old_data = crr.findByEmail(email);
		if(old_data != null) {
			old_data.setName(new_data.getName());
			old_data.setPassword(new_data.getPassword());
			old_data.setEmail(new_data.getEmail());
			
			crr.save(old_data);
			
			return ResponseEntity.accepted().body("Data updated successfully");
		}
		return ResponseEntity.badRequest().body("Email not registered");
	} 
}