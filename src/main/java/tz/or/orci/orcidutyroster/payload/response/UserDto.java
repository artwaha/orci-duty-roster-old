package tz.or.orci.orcidutyroster.payload.response;

import lombok.Data;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.model.entities.Designation;

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
    private Designation designation;
    private Department department;
    private List<Role> roles;
}