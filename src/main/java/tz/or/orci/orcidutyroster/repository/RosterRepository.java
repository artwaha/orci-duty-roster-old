package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Roster;
import tz.or.orci.orcidutyroster.model.entities.Workstation;

import java.time.LocalDate;
import java.util.Optional;

public interface RosterRepository extends JpaRepository<Roster, Long> {
    Optional<Roster> findByCreatedByAndWorkstationAndStartDateAndEndDate(String createdBy, Workstation workstation, LocalDate startDate, LocalDate endDate);
}