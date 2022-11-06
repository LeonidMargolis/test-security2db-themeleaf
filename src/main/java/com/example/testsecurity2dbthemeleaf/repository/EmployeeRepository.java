package com.example.testsecurity2dbthemeleaf.repository;

import com.example.testsecurity2dbthemeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
