package at.kaindorf.exa_102_springboot_jpa_employeedb.pojos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "department")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Department implements Serializable {

    @Id
    @Column(name = "dept_no")
    @JsonAlias("number")
    private String deptNo;

    @Column(name = "dept_name", length = 40, nullable = false)
    @NonNull
    @JsonAlias("name")
    private String deptName;

    @OneToOne
    @JoinColumn(name = "emp_no")
    @JsonAlias("deptManager")
    private Employee manager;

    @ToString.Exclude
    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<>();


    public void addEmployee(Employee emp){
        if(!employees.contains(emp)){
            employees.add(emp);
        }
    }
}
