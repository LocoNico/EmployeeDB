package at.kaindorf.exa_102_springboot_jpa_employeedb.controller;

import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Department;
import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Employee;
import at.kaindorf.exa_102_springboot_jpa_employeedb.repos.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @ModelAttribute("employee")
    public Employee populateEmployee() {
        return new Employee();
    }

    @GetMapping
    public String showEmployeeForm(Model model, @SessionAttribute("department") Department department) {
        model.addAttribute("department", department);
        return "employeeForm";
    }

    @PostMapping
    public String addEmployee(Model model,
                              @ModelAttribute("employee") Employee employee, Errors errors,
                              @SessionAttribute("department") Department department) {
        if (errors.hasErrors()){
            model.addAttribute("department", department);
            return "employeeForm";
        }
        department.addEmployee(employee);
        employee.setDepartment(department);
        employeeRepository.save(employee);
        return "forward:/departmentForm/sorted";
    }
}
