package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ems.entity.Attendance;
import com.ems.entity.AttendanceDetails;
import com.ems.entity.Employee;
import com.ems.service.AttendanceService;
import com.ems.service.EmpService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmpService employeeService;

    @GetMapping("/markattendance")
    public String markAttendanceForm(Model model) {
        // Populate employee list in the model
        List<Employee> employees = employeeService.getAllEmp();
        model.addAttribute("employees", employees);

        // Create a new attendance object
        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.now()); // Set default date to today
        model.addAttribute("attendance", attendance);

        return "markattendance"; // Return the view name
    }

    @PostMapping("/markattendance")
    public String markAttendance(@RequestParam("employeeIds[]") List<Integer> employeeIds,
                                 @RequestParam Map<String, String> statusMap,
                                 @RequestParam Map<String, String> intimeMap,
                                 @RequestParam Map<String, String> outtimeMap,
                                 @ModelAttribute Attendance attendance) {
        // Create attendance records
        Map<Integer, String> statuses = new HashMap<>();
        List<AttendanceDetails> attendanceDetails = new ArrayList<>();

        for (Integer employeeId : employeeIds) {
            String status = statusMap.get("status[" + employeeId + "]");
            statuses.put(employeeId, status);
            LocalTime intime;
            LocalTime outtime;

            if (status.equals("Absent")) {
                intime = LocalTime.of(0, 0); // Set a default value for "Absent"
                outtime = LocalTime.of(0, 0); // Set a default value for "Absent"
            } else {
                intime = LocalTime.parse(intimeMap.get("intime[" + employeeId + "]"));
                outtime = LocalTime.parse(outtimeMap.get("outtime[" + employeeId + "]"));
            }

            attendanceDetails.add(new AttendanceDetails(employeeId, intime, outtime, attendance, status));
        }

        attendance.setStatuses(statuses);
        attendance.setAttendanceDetails(attendanceDetails);

        attendanceService.markAttendance(attendance);

        // Redirect to the attendance marking form
        return "redirect:/markattendance";
    }

    @GetMapping("/viewattendance")
    public String getAttendanceReport(Model model,
                                     @RequestParam(name = "date", required = false) String dateParam,
                                     @RequestParam(name = "month", required = false) Integer monthParam,
                                     @RequestParam(name = "year", required = false) Integer yearParam,
                                     @RequestParam(name = "sortBy", required = false, defaultValue = "date") String sortBy) {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Set default values for date, month, and year if not provided
        LocalDate date = (dateParam != null) ? LocalDate.parse(dateParam) : currentDate;
        int month = (monthParam != null) ? monthParam : currentMonth;
        int year = (yearParam != null) ? yearParam : currentYear;

        List<Attendance> attendances;
        if (sortBy.equals("date")) {
            attendances = attendanceService.getAttendanceByDate(date);
        } else {
            attendances = attendanceService.getAttendanceByMonthAndYear(month, year);
        }

        model.addAttribute("attendances", attendances);
        model.addAttribute("employeeService", employeeService);
        model.addAttribute("currentDate", date.toString());
        model.addAttribute("currentMonth", month);
        model.addAttribute("currentYear", year);
        model.addAttribute("sortBy", sortBy);
        return "viewAttendance";
    }

    @GetMapping("/updateAttendance")
    public String updateAttendanceForm(Model model) {
        // Populate employee list in the model
        List<Employee> employees = employeeService.getAllEmp();
        model.addAttribute("employees", employees);

        return "updateAttendance"; // Return the view name
    }

    @PostMapping("/updateAttendance")
    public String updateAttendance(@RequestParam("employeeIds[]") List<Integer> employeeIds,
                                   @RequestParam Map<String, String> statusMap,
                                   @RequestParam Map<String, String> intimeMap,
                                   @RequestParam Map<String, String> outtimeMap,
                                   @RequestParam("date") String date) {
        // Get the attendance for the specified date
        LocalDate localDate = LocalDate.parse(date);
        Attendance attendance = attendanceService.getAttendanceByDate(localDate).get(0);

        // Update existing attendance details
        List<AttendanceDetails> existingDetails = attendance.getAttendanceDetails();
        for (AttendanceDetails detail : existingDetails) {
            int employeeId = detail.getEmployeeId();
            if (employeeIds.contains(employeeId)) {
                String status = statusMap.get("status[" + employeeId + "]");
                LocalTime intime;
                LocalTime outtime;

                if (status.equals("Absent")) {
                    intime = LocalTime.of(0, 0); // Set a default value for "Absent"
                    outtime = LocalTime.of(0, 0); // Set a default value for "Absent"
                } else {
                    intime = LocalTime.parse(intimeMap.get("intime[" + employeeId + "]"));
                    outtime = LocalTime.parse(outtimeMap.get("outtime[" + employeeId + "]"));
                }

                detail.setStatus(status);
                detail.setIntime(intime);
                detail.setOuttime(outtime);
            }
        }

        // Save the updated attendance
        attendanceService.markAttendance(attendance);

        // Redirect to the update attendance form
        return "redirect:/updateAttendance";
    }

    @GetMapping("/getAttendance")
    @ResponseBody
    public List<Map<String, String>> getAttendanceByDate(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<AttendanceDetails> details = attendanceService.getAttendanceByDate(localDate).get(0).getAttendanceDetails();

        List<Map<String, String>> response = new ArrayList<>();
        for (AttendanceDetails detail : details) {
            Map<String, String> map = new HashMap<>();
            map.put("employeeId", String.valueOf(detail.getEmployeeId()));
            map.put("employeeName", employeeService.getEmpById(detail.getEmployeeId()).getName());
            map.put("status", detail.getStatus());
            map.put("intime", detail.getIntime().toString());
            map.put("outtime", detail.getOuttime().toString());
            response.add(map);
        }
        return response;
    }
    @GetMapping("/download-attendance-report")
    public ResponseEntity<byte[]> downloadAttendanceReport(
            @RequestParam(name = "date", required = false) String dateParam,
            @RequestParam(name = "month", required = false) Integer monthParam,
            @RequestParam(name = "year", required = false) Integer yearParam,
            @RequestParam(name = "sortBy", required = false, defaultValue = "date") String sortBy) {

        LocalDate date = (dateParam != null) ? LocalDate.parse(dateParam) : LocalDate.now();
        int month = (monthParam != null) ? monthParam : date.getMonthValue();
        int year = (yearParam != null) ? yearParam : date.getYear();

        List<Attendance> attendances;
        if (sortBy.equals("date")) {
            attendances = attendanceService.getAttendanceByDate(date);
        } else {
            attendances = attendanceService.getAttendanceByMonthAndYear(month, year);
        }

        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Date,Employee ID,Employee Name,Status,In Time,Out Time\n");

        for (Attendance attendance : attendances) {
            for (AttendanceDetails detail : attendance.getAttendanceDetails()) {
                Employee employee = employeeService.getEmpById(detail.getEmployeeId());
                csvContent.append(attendance.getDate())
                        .append(",")
                        .append(detail.getEmployeeId())
                        .append(",")
                        .append(employee.getName())
                        .append(",")
                        .append(detail.getStatus())
                        .append(",")
                        .append(detail.getIntime())
                        .append(",")
                        .append(detail.getOuttime())
                        .append("\n");
            }
        }

        byte[] csvBytes = csvContent.toString().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "attendance_report.csv");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

}
