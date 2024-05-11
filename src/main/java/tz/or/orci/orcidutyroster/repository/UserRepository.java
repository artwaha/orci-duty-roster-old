package tz.or.orci.orcidutyroster.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.User;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    Page<User> findByActive(Boolean active, Pageable pageable);

    Page<User> findByRoles_NameAndDepartment(RoleEnum name, Department department, Pageable pageable);

    Page<User> findByRoles_NameAndDepartmentAndActive(RoleEnum name, Department department, boolean active, Pageable pageable);

    Page<User> findByRoles_NameInAndDepartment(Collection<RoleEnum> names, Department department, Pageable pageable);

    Page<User> findByRoles_NameInAndDepartmentAndActive(Collection<RoleEnum> names, Department department, boolean active, Pageable pageable);
}