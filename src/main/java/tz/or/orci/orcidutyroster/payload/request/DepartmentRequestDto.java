package tz.or.orci.orcidutyroster.payload.request;

import lombok.Getter;
import lombok.Setter;
import tz.or.orci.orcidutyroster.model.enums.ShiftEnum;
import tz.or.orci.orcidutyroster.model.enums.WorkstationEnum;

import java.util.List;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Department}
 */
@Getter
@Setter
public class DepartmentRequestDto {
    private List<Long> userDesignationIDs;
    private List<ShiftEnum> shiftNames;
    private List<WorkstationEnum> workstationNames;
}