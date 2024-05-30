package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.model.enums.WorkstationEnum;

import java.util.Optional;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
    Optional<Workstation> findByName(WorkstationEnum name);

    boolean existsByName(WorkstationEnum name);
}