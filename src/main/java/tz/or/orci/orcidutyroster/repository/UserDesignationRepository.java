package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;

import java.util.Optional;

public interface UserDesignationRepository extends JpaRepository<UserDesignation, Long> {
    Optional<UserDesignation> findByName(String name);

    boolean existsByName(String name);

}