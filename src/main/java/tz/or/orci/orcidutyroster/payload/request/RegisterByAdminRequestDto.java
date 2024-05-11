package tz.or.orci.orcidutyroster.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;

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

    private Long userCategoryId;
}
