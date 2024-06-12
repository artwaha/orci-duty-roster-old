package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Workstation;

import java.util.Optional;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
    Optional<Workstation> findByName(String name);

    boolean existsByName(String name);
}