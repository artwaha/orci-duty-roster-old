package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.model.enums.WorkstationEnum;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
    Workstation findByName(WorkstationEnum name);

    boolean existsByName(WorkstationEnum name);
}