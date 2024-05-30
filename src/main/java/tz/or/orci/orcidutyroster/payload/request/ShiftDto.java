package tz.or.orci.orcidutyroster.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.enums.ShiftEnum;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Shift}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDto {
    private ShiftEnum name;
    private String description;
    private String information;
}