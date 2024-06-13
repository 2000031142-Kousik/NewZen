package com.ems.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.entity.Employee;
import com.ems.entity.Attendance;
import com.ems.entity.AttendanceDetails;
import com.ems.entity.EmpExpenditure;
import com.ems.service.AttendanceService;
import com.ems.service.EmpExpenditureService;
import com.ems.service.EmpService;

@Controller
public class BalanceController {

    @Autowired
    private EmpService empService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmpExpenditureService empExpenditureService;

    @GetMapping("/balance")
    public String displayBalance(@RequestParam(name = "month", required = false) Integer month,
                                 @RequestParam(name = "year", required = false) Integer year,
                                 Model model) {
        // Set default values for month and year if not provided
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        List<Employee> employees = empService.getAllEmp();
        Map<Integer, Double> totalHoursMap = calculateTotalHoursWorked(employees, month, year);
        Map<Integer, Double> advanceAmountMap = calculateAdvanceAmount(employees, month, year);
        Map<Integer, Double> balanceAmountMap = calculateBalanceAmount(employees, totalHoursMap, advanceAmountMap);
        model.addAttribute("employees", employees);
        model.addAttribute("totalHoursMap", totalHoursMap);
        model.addAttribute("advanceAmountMap", advanceAmountMap);
        model.addAttribute("balanceAmountMap", balanceAmountMap);
        model.addAttribute("currentMonth", month);
        model.addAttribute("currentYear", year);
        return "balance";
    }


	private Map<Integer, Double> calculateTotalHoursWorked(List<Employee> employees, int month, int year) {
	    Map<Integer, Double> totalHoursMap = new HashMap<>();
	    LocalDate startDate = LocalDate.of(year, month, 1);
	    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
	
	    for (Employee employee : employees) {
	        double totalHours = 0.0;
	        List<Attendance> attendances = attendanceService.getAttendanceByDateRange(employee.getId(), startDate, endDate);
	        for (Attendance attendance : attendances) {
	            List<AttendanceDetails> attendanceDetails = attendance.getAttendanceDetails();
	            for (AttendanceDetails detail : attendanceDetails) {
	                if (detail.getEmployeeId() == employee.getId()) {
	                    totalHours += detail.getTotalHoursWorked();
	                }
	            }
	        }
	        totalHoursMap.put(employee.getId(), totalHours);
	    }
	
	    return totalHoursMap;
	}

	private Map<Integer, Double> calculateAdvanceAmount(List<Employee> employees, int month, int year) {
	    Map<Integer, Double> advanceAmountMap = new HashMap<>();
	    LocalDate startDate = LocalDate.of(year, month, 1);
	    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
	
	    for (Employee employee : employees) {
	        double totalAdvanceAmount = 0.0;
	        List<EmpExpenditure> expenditures = empExpenditureService.getExpenditureByEmployeeAndDateRange(employee.getId(), startDate, endDate);
	        for (EmpExpenditure expenditure : expenditures) {
	            totalAdvanceAmount += expenditure.getAdvanceAmount();
	        }
	        advanceAmountMap.put(employee.getId(), totalAdvanceAmount);
	    }
	
	    return advanceAmountMap;
	}

    private Map<Integer, Double> calculateBalanceAmount(List<Employee> employees, Map<Integer, Double> totalHoursMap, Map<Integer, Double> advanceAmountMap) {
        Map<Integer, Double> balanceAmountMap = new HashMap<>();
        for (Employee employee : employees) {
            double balanceAmount = (employee.getSalary() / employee.getSlab()) * totalHoursMap.get(employee.getId()) - advanceAmountMap.get(employee.getId());
            balanceAmountMap.put(employee.getId(), balanceAmount);
        }
        return balanceAmountMap;
    }
    @GetMapping("/download-balance-report")
    public ResponseEntity<byte[]> downloadBalanceReport(@RequestParam(name = "month", required = false) Integer month,
                                                        @RequestParam(name = "year", required = false) Integer year) {
        // Set default values for month and year if not provided
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        List<Employee> employees = empService.getAllEmp();
        Map<Integer, Double> totalHoursMap = calculateTotalHoursWorked(employees, month, year);
        Map<Integer, Double> advanceAmountMap = calculateAdvanceAmount(employees, month, year);
        Map<Integer, Double> balanceAmountMap = calculateBalanceAmount(employees, totalHoursMap, advanceAmountMap);

        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Employee ID,Employee Name,Total Hours Worked (Monthly),Total Advance Amount (Monthly),Balance Amount\n");

        for (Employee employee : employees) {
            csvContent.append(employee.getId())
                    .append(",")
                    .append(employee.getName())
                    .append(",")
                    .append(totalHoursMap.get(employee.getId()))
                    .append(",")
                    .append(advanceAmountMap.get(employee.getId()))
                    .append(",")
                    .append(balanceAmountMap.get(employee.getId()))
                    .append("\n");
        }

        byte[] csvBytes = csvContent.toString().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "balance_report.csv");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}


