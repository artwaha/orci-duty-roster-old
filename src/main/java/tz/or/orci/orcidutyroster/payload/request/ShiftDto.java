package tz.or.orci.orcidutyroster.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Shift}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDto {
    private String name;
    private String description;
    private Long departmentId;
}