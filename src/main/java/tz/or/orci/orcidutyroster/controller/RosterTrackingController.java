package tz.or.orci.orcidutyroster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tz.or.orci.orcidutyroster.model.entities.RosterTracking;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;

@RequestMapping("api/v1/roster-tracking")
@Tag(name = "Roster Tracking Controller")
@SecurityRequirement(name = "jwt")
public interface RosterTrackingController {
    @GetMapping("{rosterId}")
    @Operation(summary = "Get Roster Tracking by Roster Id")
    ResponseEntity<GenericResponse<RosterTracking>> getRosterTrackingByRosterId(
            @PathVariable @Valid Long rosterId,
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @Valid @RequestParam(defaultValue = "10", required = false) int pageSize
    );
}
