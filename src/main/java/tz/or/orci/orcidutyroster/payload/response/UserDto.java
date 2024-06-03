package tz.or.orci.orcidutyroster.payload.response;

import lombok.Data;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;
import tz.or.orci.orcidutyroster.model.entities.Workstation;

import java.util.List;

/**
 * DTO for {@link tz.or.orci.orcidutyroster.model.entities.User}
 */
@Data
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private boolean active;
    private UserDesignation userDesignation;
    private DepartmentDto department;
    private List<Role> roles;
    private List<Workstation> workstations;
}