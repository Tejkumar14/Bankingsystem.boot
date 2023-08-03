package org.jsp.banking_system.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.jsp.banking_system.dto.BankAccount;
import org.jsp.banking_system.dto.Customer;
import org.jsp.banking_system.dto.Login;
import org.jsp.banking_system.exception.MyException;
import org.jsp.banking_system.helper.Mailvarification;
import org.jsp.banking_system.helper.ResponceStructure;
import org.jsp.banking_system.repository.CustomerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	CustomerRespository repository;
	@Autowired
	Mailvarification mailvarification;

	@Autowired
	BankAccount account;
@Autowired

	public ResponceStructure<Customer> save(Customer customer) {
		ResponceStructure<Customer> structure = new ResponceStructure<>();
		int age = Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears();
		customer.getAge();
		if (age < 18) {
			structure.setMassage("you should be 18+ to created account");
			structure.setCode(HttpStatus.CONFLICT.value());
			structure.setData(customer);
		} else {
			Random random = new Random();
			int otp = random.nextInt(100000, 999999);
			customer.setOtp(otp);

			// mailvarification.sendMail(customer);

			structure.setMassage("Varifiction mail send");
			structure.setCode(HttpStatus.PROCESSING.value());
			structure.setData(repository.save(customer));
		}

		return structure;
	}

	public ResponceStructure<Customer> verify(int custid, int otp) throws MyException {
		ResponceStructure<Customer> structure = new ResponceStructure<>();

		Optional<Customer> optional = repository.findById(custid);
		if (optional.isEmpty()) {
			throw new MyException("Check Id and Try Again");
		} else {
			Customer customer = optional.get();
			if (customer.getOtp() == otp) {
				structure.setCode(HttpStatus.CREATED.value());
				structure.setMassage("Account Created Sucessfully");
				customer.setStatus(true);

				structure.setData(repository.save(customer));
			} else {
				throw new MyException("Otp Missmatch");
			}
		}
		return structure;
	}

	public ResponceStructure<Customer> login(Login login) throws MyException {
		ResponceStructure<Customer> structure = new ResponceStructure<>();
		java.util.Optional<Customer> optional = repository.findById(login.getId());
		if (optional.isEmpty()) {
			throw new MyException("Invalid Customer Id");
		} else {
			Customer customer = optional.get();
			if (customer.getPassword().equals(login.getPassword())) {
				if (customer.isStatus()) {
					structure.setCode(HttpStatus.ACCEPTED.value());
					structure.setMassage("Logoin Success");
					structure.setData(customer);
				} else {
					throw new MyException("Varify your email first");
				}
			} else {
				throw new MyException("Invalid Password");
			}
		}
		return structure;

	}

	public ResponceStructure<Customer> createAccount(int cust_id, String type) throws MyException {
		ResponceStructure<Customer> structure = new ResponceStructure<>();
		Optional<Customer> optional = repository.findById(cust_id);
		if (optional.isEmpty()) {
			throw new MyException("invalid customer ID");
		} else {
			Customer customer = optional.get();
			List<BankAccount> list = customer.getAccounts();
			boolean flag = true;
			for (BankAccount account : list) {
				if (account.getType().equals(type)) {
					flag = false;
					break;
				}
			}
			if (!flag) {
				throw new MyException(type + "account already exists");
			} else {
				account.setType(type);
				if (type.equals("savings")) {
					account.setBanklimit(5000);

				} else {
					account.setBanklimit(10000);
				}
				list.add(account);
				customer.setAccounts(list);
			}
			structure.setCode(HttpStatus.ACCEPTED.value());
			structure.setMassage("Account creayed wait for management to approve");
			structure.setData(repository.save(customer));

		}

		return structure;
	}

}
