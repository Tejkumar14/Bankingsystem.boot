package org.jsp.banking_system.controller;



import org.jsp.banking_system.dto.Customer;
import org.jsp.banking_system.dto.Login;
import org.jsp.banking_system.exception.MyException;
import org.jsp.banking_system.helper.ResponceStructure;
import org.jsp.banking_system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	CustomerService service;

	@PostMapping("add")
	public ResponceStructure<Customer> save(@RequestBody Customer customer) {
		return service.save(customer);
	
	}
	@PutMapping("otp/{cust_id}/{otp}")
	public ResponceStructure<Customer> otpVerify(@PathVariable int cust_id ,@PathVariable int otp) throws MyException{
		return service.verify(cust_id,otp);
		
	}
	@PostMapping("login")
	public ResponceStructure<Customer> login(@RequestBody Login login) throws MyException{
		
		return service.login(login);
		
	}
	@PostMapping("account/{cust_id}/{type}")
	public ResponceStructure<Customer> createAccount(@PathVariable int cust_id,@PathVariable String type ) throws MyException{
		return service.createAccount(cust_id, type);
	}
	
}
