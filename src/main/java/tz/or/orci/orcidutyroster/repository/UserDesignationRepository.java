package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;

public interface UserDesignationRepository extends JpaRepository<UserDesignation, Long> {
    boolean existsByName(String name);
}