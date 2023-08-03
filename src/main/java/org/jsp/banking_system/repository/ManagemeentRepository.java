package org.jsp.banking_system.repository;

import java.util.Optional;

import org.jsp.banking_system.dto.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagemeentRepository extends JpaRepository<Management, Integer> {

	Management findByEmail(String email);



}
