package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.RosterTracking;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.RosterRepository;
import tz.or.orci.orcidutyroster.repository.RosterTrackingRepository;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class RosterTrackingService {
    private final RosterRepository rosterRepository;
    private final RosterTrackingRepository rosterTrackingRepository;
    private final Utils utils;

    public GenericResponse<RosterTracking> getRosterTrackingByRosterId(Long rosterId, int pageNumber, int pageSize) {
        boolean rosterExists = rosterRepository.existsById(rosterId);
        if (!rosterExists)
            throw new EntityNotFoundException("Roster with Id " + rosterId + " not found");

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RosterTracking> rosterTrackingPage = rosterTrackingRepository.findByRoster_Id(rosterId, pageable);
        return utils.generateGenericResponse(
                OK.value(),
                rosterTrackingPage.getNumber(),
                rosterTrackingPage.getSize(),
                rosterTrackingPage.getTotalPages(),
                rosterTrackingPage.getTotalElements(),
                rosterTrackingPage.getContent());
    }
}
