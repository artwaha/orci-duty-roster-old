package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tz.or.orci.orcidutyroster.model.entities.Roster;
import tz.or.orci.orcidutyroster.payload.request.RosterDto;

@RequestMapping("api/v1/rosters")
@Tag(name = "Roster Controller", description = "Supervisor | HOD Protected")
@SecurityRequirement(name = "jwt")
@PreAuthorize("hasAnyRole('SUPERVISOR','HEAD_OF_DEPARTMENT')")
public interface RosterController {
    @PostMapping()
    @Operation(summary = "Generate Roster")
    ResponseEntity<Roster> generateRoster(@Valid @RequestBody RosterDto rosterDto);
}
