package org.jsp.banking_system.helper;

import org.jsp.banking_system.dto.Customer;

import lombok.Data;

@Data
public class ResponceStructure<T> {
int code;
String massage;
T Data;
}
