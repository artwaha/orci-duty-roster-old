package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.enums.DepartmentEnum;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(DepartmentEnum name);
    Optional<Department> findByName(DepartmentEnum name);
}