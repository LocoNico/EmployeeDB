package at.kaindorf.exa_102_springboot_jpa_employeedb.pojos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


@Entity(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class Employee implements Serializable {

    @Id
    @Column(name = "emp_no")
    @JsonAlias("emp_no")
    @NotNull(message = "number is required")
    @NonNull
    private Integer empNo;

    @Column(name = "birth_date", nullable = false)
    @JsonAlias("birthDate")
    @NotNull(message = "date of birth is required")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NonNull
    private LocalDate dateOfBirth;

    @Column(name = "lastname", length = 16, nullable = false)
    @NotBlank(message = "lastname is required")
    @NonNull
    private String lastname;

    @Column(name = "firstname", length = 14, nullable = false)
    @NotBlank(message = "firstname is required")
    @NonNull
    private String firstname;

    @Column(name = "gender", length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "gender is required")
    @NonNull
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "dept_no")
    @ToString.Exclude
    private Department department;
}
