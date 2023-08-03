package org.jsp.banking_system.controller;

import java.util.List;

import org.jsp.banking_system.dto.BankAccount;
import org.jsp.banking_system.dto.Management;
import org.jsp.banking_system.exception.MyException;
import org.jsp.banking_system.helper.ResponceStructure;
import org.jsp.banking_system.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management")
public class ManagementController {
	@Autowired
	ManagementService service;

	@PostMapping("add")
	public ResponceStructure<Management> save(@RequestBody Management management) {

		return service.save(management);

	}
	@PostMapping("login")
	public ResponceStructure<Management> login(@RequestBody Management management ) throws MyException{
		return  service.login(management);
		
	}
	@GetMapping("accounts")
	public ResponceStructure<List<BankAccount>>fetchAllAccount() throws MyException{
		return service.fetchAllAccount();
		
	}
}
