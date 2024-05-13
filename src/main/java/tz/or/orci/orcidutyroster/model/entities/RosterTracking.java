package tz.or.orci.orcidutyroster.model.entities;

import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.auditable.AuditableCreated;
import tz.or.orci.orcidutyroster.model.enums.RosterStatusEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "roster_tracking")
public class RosterTracking extends AuditableCreated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Roster roster;

    @Enumerated(EnumType.STRING)
    private RosterStatusEnum status;

    private String remarks;
}