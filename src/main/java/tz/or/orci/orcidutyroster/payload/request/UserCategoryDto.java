package tz.or.orci.orcidutyroster.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.entities.Designation;

/**
 * DTO for {@link Designation}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryDto {
    private String name;
    private Long departmentId;
}