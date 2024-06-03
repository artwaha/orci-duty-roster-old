package tz.or.orci.orcidutyroster.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;

/**
 * DTO for {@link UserDesignation}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDesignationDto {
    private String name;
}