package tz.or.orci.orcidutyroster.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.auditable.AuditableWithUser;

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

    @ManyToOne
    @JoinColumn
    private RosterDuration duration;

    @ManyToOne
    private Workstation workstation;

    @OneToMany(mappedBy = "roster", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShiftAssignment> shiftAssignments = new ArrayList<>();

}
