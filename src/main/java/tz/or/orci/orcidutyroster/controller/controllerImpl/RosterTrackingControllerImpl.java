package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.RosterTrackingController;
import tz.or.orci.orcidutyroster.model.entities.RosterTracking;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.service.RosterTrackingService;

@RestController
@RequiredArgsConstructor
public class RosterTrackingControllerImpl implements RosterTrackingController {
    private final RosterTrackingService rosterTrackingService;

    @Override
    public ResponseEntity<GenericResponse<RosterTracking>> getRosterTrackingByRosterId(Long rosterId, int pageNumber, int pageSize) {
        return ResponseEntity.ok(rosterTrackingService.getRosterTrackingByRosterId(rosterId, pageNumber, pageSize));
    }
}
