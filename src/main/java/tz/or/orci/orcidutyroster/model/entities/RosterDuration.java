package tz.or.orci.orcidutyroster.model.entities;

import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.enums.RosterDurationEnum;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "roster_durations")
public class RosterDuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RosterDurationEnum durationType;

    private LocalDate start_date;

    private LocalDate end_date;
}
