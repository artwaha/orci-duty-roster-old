package tz.or.orci.orcidutyroster.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Workstation}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkstationDto {
    private String name;
    private Long departmentId;
}