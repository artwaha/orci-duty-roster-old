package tz.or.orci.orcidutyroster.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.enums.DepartmentEnum;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;
import tz.or.orci.orcidutyroster.model.enums.WorkstationEnum;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterByAdminRequestDto {
    @NotBlank(message = "Username is Required")
    private String username;

    @NotBlank(message = "Full name is Required")
    private String fullName;

    @NotEmpty(message = "User must be assigned at least one role")
    private List<RoleEnum> roles;

    @NotNull(message = "Department is Required")
    private DepartmentEnum departmentName;

    private Long userDesignationId;

    @NotEmpty(message = "Workstation is Required")
    private List<WorkstationEnum> workstationNames;
}
