package at.kaindorf.exa_102_springboot_jpa_employeedb.controller;

import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Department;
import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Employee;
import at.kaindorf.exa_102_springboot_jpa_employeedb.repos.DepartmentRepository;
import at.kaindorf.exa_102_springboot_jpa_employeedb.repos.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/departmentForm")
@SessionAttributes({"department", "sortOrder"})
public class DepartmentController {

    private List<Department> departmentList;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @ModelAttribute
    public void addAttribute(Model model){
        departmentList = departmentRepository.findAll();
        departmentList.sort(Comparator.comparing(Department::getDeptName));
        model.addAttribute("departmentList", departmentList);
    }

    @ModelAttribute("department")
    public Department populateDepartment() {
        return new Department();
    }

    @ModelAttribute("sortOrder")
    public boolean populateSortOrder() {
        return Boolean.FALSE;
    }

    @GetMapping
    public String showDesign(){
        return "departmentForm";
    }

    @PostMapping
    public String showEmployees(Model model, @RequestParam("deptNo") String deptNo) {
        Department department = departmentRepository.findDepartmentByDeptNo(deptNo);
        updateModel(model, department, null);
        model.addAttribute("department", department);
        model.addAttribute("sortOrder", Boolean.TRUE);
        return "departmentForm";
    }

    private void updateModel(Model model, Department department, Comparator comparator) {
        department = departmentRepository.findDepartmentByDeptNo(department.getDeptNo());
        List<Employee> employees = department.getEmployees();
        employees.remove(department.getManager());
        if(comparator != null) {
            employees.sort(comparator);
        }
        model.addAttribute("manager", department.getManager());
        model.addAttribute("employees", employees);
        model.addAttribute("selDept", department.getDeptName());
    }

    @PostMapping("/{id}")
    public String removeEmployees(Model model, @SessionAttribute("department") Department department,
                                  @PathVariable("id") int employeeNo) {
        Employee employee = employeeRepository.findById(employeeNo).get();
        employeeRepository.deleteById(employeeNo);
        department.getEmployees().remove(employee);
        departmentRepository.flush();
        updateModel(model, department, Comparator.comparing(Employee::getLastname).thenComparing(Employee::getFirstname));
        return "departmentForm";
    }

    @PostMapping("/sorted")
    public String sortEmployees(Model model, @SessionAttribute("department") Department department, @SessionAttribute("sortOrder") Boolean sortOrder) {
        Comparator comparator = Comparator.comparing(Employee::getLastname).thenComparing(Employee::getFirstname);
        if (!sortOrder) {
            comparator = comparator.reversed();
        }
        sortOrder = !sortOrder;
        updateModel(model, department, comparator);
        model.addAttribute("sortOrder", sortOrder);
        return "departmentForm";
    }
}
