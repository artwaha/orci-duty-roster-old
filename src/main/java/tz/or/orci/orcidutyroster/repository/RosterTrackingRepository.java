package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.RosterTracking;

public interface RosterTrackingRepository extends JpaRepository<RosterTracking, Long> {
    Page<RosterTracking> findByRoster_Id(Long id, Pageable pageable);
}