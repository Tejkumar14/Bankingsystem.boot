package org.jsp.banking_system.service;

import java.util.List;

import org.jsp.banking_system.dto.BankAccount;

import org.jsp.banking_system.dto.Management;
import org.jsp.banking_system.exception.MyException;
import org.jsp.banking_system.helper.ResponceStructure;
import org.jsp.banking_system.repository.BankRepository;
import org.jsp.banking_system.repository.ManagemeentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
	@Autowired
	ManagemeentRepository repository;
	@Autowired
	BankRepository repository2;

	public ResponceStructure<Management> save(Management management) {

		ResponceStructure<Management> structure = new ResponceStructure<>();
		structure.setCode(HttpStatus.CREATED.value());
		structure.setData(repository.save(management));
		structure.setMassage("Account Creatred Successfully");
		return structure;

	}

	public ResponceStructure<Management> login(Management management) throws MyException {
		ResponceStructure<Management> structure = new ResponceStructure<>();
		Management management1 = repository.findByEmail(management.getEmail());
		if (management1 == null) {
			throw new MyException("Invalid Management Id");
		} else {

			if (management1.getPassword().equals(management.getPassword())) {

				structure.setCode(HttpStatus.ACCEPTED.value());
				structure.setMassage("Logoin Success");
				structure.setData(management1);
			} else {
				throw new MyException("Invalid Password");
			}
		}
		return structure;
	}

	public ResponceStructure<List<BankAccount>> fetchAllAccount() throws MyException {
		ResponceStructure<List<BankAccount>> structure = new ResponceStructure<>();

		List<BankAccount> list = repository2.findAll();
		if (list.isEmpty()) {
			throw new MyException("No Account Present");
		} else {
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMassage("Data Found");
			structure.setData(list);
		}
		return structure;
	}
}
 