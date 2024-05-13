package tz.or.orci.orcidutyroster.controller.controllerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tz.or.orci.orcidutyroster.controller.RosterController;
import tz.or.orci.orcidutyroster.model.entities.Roster;
import tz.or.orci.orcidutyroster.payload.request.RosterDto;
import tz.or.orci.orcidutyroster.service.RosterService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class RosterControllerImpl implements RosterController {
    private final RosterService rosterService;

    @Override
    public ResponseEntity<Roster> generateRoster(RosterDto rosterDto) {
        return ResponseEntity.status(CREATED).body(rosterService.generateRoster(rosterDto));
    }
}
