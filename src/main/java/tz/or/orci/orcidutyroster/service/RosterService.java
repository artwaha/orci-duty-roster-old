package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Roster;
import tz.or.orci.orcidutyroster.model.entities.User;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.payload.request.RosterDto;
import tz.or.orci.orcidutyroster.repository.RosterRepository;
import tz.or.orci.orcidutyroster.repository.WorkstationRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import java.util.Optional;

import static tz.or.orci.orcidutyroster.model.enums.RosterStatusEnum.CREATED;

@Service
@RequiredArgsConstructor
public class RosterService {

    private final RosterRepository rosterRepository;
    private final Utils utils;
    private final WorkstationRepository workstationRepository;
    private final ModelMapper modelMapper;

    public Roster generateRoster(RosterDto rosterDto) {
        User authenticatedUser = utils.getAuthenticatedUser();
        Workstation workstation = workstationRepository.findById(rosterDto.getWorkstationId()).orElseThrow(() -> new EntityNotFoundException("Workstation with Id " + rosterDto.getWorkstationId() + " not found"));
        Optional<Roster> roster = rosterRepository.findByCreatedByAndWorkstationAndStartDateAndEndDate(authenticatedUser.getUsername(), workstation, rosterDto.getStartDate(), rosterDto.getEndDate());

        if (roster.isPresent())
            throw new CustomException("Roster for " + workstation.getName() + " on " + rosterDto.getStartDate() + " - " + rosterDto.getEndDate() + " already exists");

        Roster newRoster = modelMapper.map(rosterDto, Roster.class);
        newRoster.setRosterStatus(CREATED);
        newRoster.setWorkstation(workstation);
        Roster savedRoster = rosterRepository.save(newRoster);

        utils.saveRosterTrackingDetails(savedRoster, null);

        return savedRoster;
    }
}
