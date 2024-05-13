package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.payload.request.RoleDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/roles")
@Tag(name = "Role Controller")
@SecurityRequirement(name = "jwt")
public interface RoleController {
    @GetMapping()
    @Operation(summary = "Get All Roles", description = "Admin | Supervisor | HOD Protected")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','HEAD_OF_DEPARTMENT')")
    ResponseEntity<GenericResponse<Role>> getAllRoles(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update Role", description = "Admin Protected")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Role> updateRole(
            @Valid @PathVariable Long id,
            @Valid @RequestBody RoleDto roleDto
    );
}
