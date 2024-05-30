package tz.or.orci.orcidutyroster.model.entities;

import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.enums.WorkstationEnum;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "workstations")
public class Workstation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkstationEnum name;

    @ManyToOne()
    private Department department;
}
