package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tz.or.orci.orcidutyroster.model.entities.UserCategory;
import tz.or.orci.orcidutyroster.payload.request.UserCategoryDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/user-categories")
@Tag(name = "User Category Controller", description = "Admin Protected")
@SecurityRequirement(name = "jwt")
public interface UserCategoryController {
    @PostMapping()
    @Operation(summary = "Add User Category")
    ResponseEntity<UserCategory> addUserCategory(@Valid @RequestBody UserCategoryDto userCategoryDto);

    @GetMapping("{id}")
    @Operation(summary = "Get User Categories by Id")
    ResponseEntity<UserCategory> getUserCategoryById(@PathVariable @Valid Long id);

    @GetMapping()
    @Operation(summary = "Get All User Categories")
    ResponseEntity<GenericResponse<UserCategory>> getAllUserCategories(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @GetMapping("departments/{id}")
    @Operation(summary = "Get User Categories by Department")
    ResponseEntity<GenericResponse<UserCategory>> getAllUserCategoriesByDepartment(
            @PathVariable @Valid Long id,
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );

    @PatchMapping("{id}")
    @Operation(summary = "Update User Category")
    ResponseEntity<UserCategory> updateUserCategory(
            @Valid @PathVariable Long id,
            @Valid @RequestBody UserCategoryDto userCategoryDto
    );
}
