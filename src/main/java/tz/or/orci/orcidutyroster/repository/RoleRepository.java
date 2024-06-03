package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum role);

    boolean existsByName(RoleEnum name);
}