package com.ems.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.EmpExpenditure;
import com.ems.entity.Employee;
import com.ems.repository.EmpExpenditureRepo;
import com.ems.repository.EmpRepo;

@Service
public class EmpExpenditureService {
    
    @Autowired
    private EmpExpenditureRepo expenditureRepo;
    
    @Autowired
    private EmpRepo employeeRepository;
    
    public void saveExpenditure(EmpExpenditure expenditure) {
        expenditureRepo.save(expenditure);
    }
    
    public List<EmpExpenditure> getExpenditureByEmployeeAndDateRange(int employeeId, LocalDate startDate, LocalDate endDate) {
        return expenditureRepo.findByEmployeeAndDateRange(employeeId, startDate, endDate);
    }
    
    public List<EmpExpenditure> getAllEmpExpenditures() {
        return expenditureRepo.findAll();
    }

    public EmpExpenditure findById(int id) {
        Optional<EmpExpenditure> expenditure = expenditureRepo.findById(id);
        if (expenditure.isPresent()) {
            EmpExpenditure empExpenditure = expenditure.get();
            int employeeId = empExpenditure.getEmployee().getId();
            Employee employee = employeeRepository.findById(employeeId).orElse(null);
            empExpenditure.setEmployee(employee);
            return empExpenditure;
        }
        return null;
    }
}
