package tz.or.orci.orcidutyroster.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelfRegisterRequestDto {
    @NotBlank(message = "Username is Required")
    private String username;

    @NotBlank(message = "Full name is Required")
    private String fullName;

    @NotNull(message = "Role is Required")
    private RoleEnum role;

}
