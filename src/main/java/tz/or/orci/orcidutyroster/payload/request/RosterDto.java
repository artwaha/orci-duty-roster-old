package tz.or.orci.orcidutyroster.payload.request;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.enums.RosterDurationEnum;

import java.time.LocalDate;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Roster}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RosterDto {
    private RosterDurationEnum durationType;
    @FutureOrPresent(message = "Start Date has to be the present day or day in future")
    private LocalDate startDate;
    @FutureOrPresent(message = "Start Date has to be the present day or day in future")
    private LocalDate endDate;
    private Long workstationId;
}