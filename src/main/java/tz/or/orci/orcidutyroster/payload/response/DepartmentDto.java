package tz.or.orci.orcidutyroster.payload.response;

import lombok.Data;
import tz.or.orci.orcidutyroster.model.enums.DepartmentEnum;

import java.io.Serializable;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Department}
 */
@Data
public class DepartmentDto implements Serializable {
    private Long id;
    private DepartmentEnum name;
}