package at.kaindorf.exa_102_springboot_jpa_employeedb.repos;

import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Department;
import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@Slf4j
public class InitDB {

    @Autowired
    private DepartmentRepository deptRepo;

    @Autowired
    private EmployeeRepository empRepo;

    @PostConstruct
    public void initDatabaseFromJSON() {
        Path jsonPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "employeedb.json");
        ObjectMapper om = new ObjectMapper();
        List<Department> departments;
        try {
            departments = om.readValue(jsonPath.toFile(), new TypeReference<List<Department>>(){});
            if( departments != null) {
                for (Department dept: departments) {
                    empRepo.saveAllAndFlush(dept.getEmployees());
                    empRepo.save(dept.getManager());
                    deptRepo.save(dept);

                    for(Employee employee : dept.getEmployees()) {
                        employee.setDepartment(dept);
                    }
                    empRepo.saveAllAndFlush(dept.getEmployees());
                }
            } else {
                log.error("JSON error");
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}
