package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.payload.request.DepartmentRequestDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/departments")
@Tag(name = "Department Controller")
public interface DepartmentController {
    @PostMapping()
    @Operation(summary = "Add Department", description = "Admin Protected")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Department> addDepartment(@Valid @RequestBody DepartmentRequestDto department);

    @GetMapping("{id}")
    @Operation(summary = "Get Department by Id", description = "Admin Protected")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Department> getDepartmentById(@PathVariable @Valid Long id);

    @GetMapping()
    @Operation(summary = "Get All Departments")
    ResponseEntity<GenericResponse<Department>> getAllDepartments(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update Department", description = "Admin Protected")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Department> updateDepartment(
            @Valid @PathVariable Long id,
            @Valid @RequestBody DepartmentRequestDto department
    );
}
