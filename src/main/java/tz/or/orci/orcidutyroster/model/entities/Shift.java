package tz.or.orci.orcidutyroster.model.entities;

import jakarta.persistence.*;
import lombok.*;
import tz.or.orci.orcidutyroster.model.enums.ShiftEnum;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ShiftEnum name;

    private boolean claimable = true;
}
