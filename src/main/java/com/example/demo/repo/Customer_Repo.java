package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bin.Customer;

import jakarta.transaction.Transactional;

@Repository
public interface Customer_Repo extends CrudRepository<Customer, Integer> {
	
	public Customer findByEmail(String e);
	
	public Customer findByEmailAndPassword(String e, String p);
	
	@Transactional
	public void deleteByEmail(String email);

}
