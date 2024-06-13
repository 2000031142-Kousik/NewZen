package com.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.ems.entity.Employee;
import com.ems.repository.EmpRepo;

@Service
public class EmpService {
	
    
    @Autowired
    private EmpRepo repo;
    
    public void addEmp(Employee e) {
        repo.save(e);
    }
    
    public List<Employee> getAllEmp() {
        return repo.findAll();
    }
    
    public Employee getEmpById(int id) {
        Optional<Employee> e = repo.findById(id);
        return e.orElse(null);
    }
    
    public void updateEmp(Employee e) {
        // Check if the employee exists in the database
        Optional<Employee> existingEmployee = repo.findById(e.getId());
        if (existingEmployee.isPresent()) {
            // Update the existing employee
            repo.save(e);
        } else {
            // Handle error: employee not found
            throw new RuntimeException("Employee not found with id: " + e.getId());
        }
    }
    
    public void deleteEmp(int id) {
        repo.deleteById(id);
    }
    
}
