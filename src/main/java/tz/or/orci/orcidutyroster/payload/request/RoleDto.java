package tz.or.orci.orcidutyroster.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.Role}
 */
@Getter
@Setter
public class RoleDto {
    @NotBlank(message = "Role Description is required")
    private String description;
}