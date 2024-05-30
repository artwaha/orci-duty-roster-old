package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.model.entities.Designation;
import tz.or.orci.orcidutyroster.payload.request.UserCategoryDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/user-categories")
@Tag(name = "User Category Controller")
@SecurityRequirement(name = "jwt")
public interface DesignationController {
    @PostMapping()
    @Operation(summary = "Add User Category", description = "Admin Protected")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Designation> addUserCategory(@Valid @RequestBody UserCategoryDto userCategoryDto);

    @GetMapping("{id}")
    @Operation(summary = "Get User Categories by Id")
    ResponseEntity<Designation> getUserCategoryById(@PathVariable @Valid Long id);

    @GetMapping()
    @Operation(summary = "Get All User Categories")
    ResponseEntity<GenericResponse<Designation>> getAllUserCategories(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @GetMapping("departments/{id}")
    @Operation(summary = "Get User Categories by Department")
    ResponseEntity<GenericResponse<Designation>> getAllUserCategoriesByDepartment(
            @PathVariable @Valid Long id,
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update User Category", description = "Admin Protected")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Designation> updateUserCategory(
            @Valid @PathVariable Long id,
            @Valid @RequestBody UserCategoryDto userCategoryDto
    );
}
