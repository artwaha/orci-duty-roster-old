package tz.or.orci.orcidutyroster.model.entities;

import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
