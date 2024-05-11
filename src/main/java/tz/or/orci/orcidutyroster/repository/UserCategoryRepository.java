package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.UserCategory;

import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    Optional<UserCategory> findByNameIgnoreCase(String name);

    Page<UserCategory> findByDepartment(Department department, Pageable pageable);
}