package tz.or.orci.orcidutyroster.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.enums.DepartmentEnum;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DepartmentEnum name;

    @ManyToMany
    private List<UserDesignation> userDesignations = new ArrayList<>();

    @ManyToMany
    private List<Shift> shifts = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Workstation> workstations = new ArrayList<>();

}
