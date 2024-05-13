package tz.or.orci.orcidutyroster.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.auditable.AuditableWithUser;
import tz.or.orci.orcidutyroster.model.enums.RosterDurationEnum;
import tz.or.orci.orcidutyroster.model.enums.RosterStatusEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "rosters")
public class Roster extends AuditableWithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RosterDurationEnum durationType;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    private Workstation workstation;

    @Enumerated(EnumType.STRING)
    private RosterStatusEnum rosterStatus;

    @OneToMany(mappedBy = "roster", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShiftAssignment> shiftAssignments = new ArrayList<>();

}
