package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Designation;

import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<Designation, Long> {
    Optional<Designation> findByNameIgnoreCase(String name);

    Page<Designation> findByDepartment(Department department, Pageable pageable);
}