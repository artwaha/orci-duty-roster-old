package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.RoleController;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.RoleService;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {
    private final RoleService roleService;

    @Override
    public ResponseEntity<GenericResponse<Role>> getAllRoles(int pageNumber, int pageSize) {
        return ResponseEntity.ok(roleService.getAllRoles(pageNumber, pageSize));
    }
}
