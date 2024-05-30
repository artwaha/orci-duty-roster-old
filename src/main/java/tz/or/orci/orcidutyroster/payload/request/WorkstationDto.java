package tz.or.orci.orcidutyroster.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.enums.WorkstationEnum;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Workstation}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkstationDto {
    private WorkstationEnum name;
    private String description;
    private String information;
}