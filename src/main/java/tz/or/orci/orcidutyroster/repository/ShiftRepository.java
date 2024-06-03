package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Shift;
import tz.or.orci.orcidutyroster.model.enums.ShiftEnum;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    Shift findByName(ShiftEnum name);

    boolean existsByName(ShiftEnum name);
}