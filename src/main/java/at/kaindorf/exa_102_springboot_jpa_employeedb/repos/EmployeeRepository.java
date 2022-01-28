package at.kaindorf.exa_102_springboot_jpa_employeedb.repos;

import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}