package org.jsp.banking_system.exception;

import org.jsp.banking_system.helper.ResponceStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value = MyException.class)
	public ResponseEntity<ResponceStructure<String>> idNotFound(MyException ie) {
		ResponceStructure<String> responseStructure = new ResponceStructure<String>();
		responseStructure.setCode(HttpStatus.NOT_FOUND.value());
		responseStructure.setMassage("Request failed");
		responseStructure.setData(ie.toString());
		return new ResponseEntity<ResponceStructure<String>>(responseStructure, HttpStatus.NOT_ACCEPTABLE);
            
	}
}
