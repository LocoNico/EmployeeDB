package at.kaindorf.exa_102_springboot_jpa_employeedb.repos;

import at.kaindorf.exa_102_springboot_jpa_employeedb.pojos.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select d from department d where d.deptNo = ?1")
    Department findDepartmentByDeptNo(String deptNo);

}