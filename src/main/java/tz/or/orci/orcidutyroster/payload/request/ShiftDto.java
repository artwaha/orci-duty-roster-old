package tz.or.orci.orcidutyroster.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Boolean claimable;
}