package tz.or.orci.orcidutyroster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.model.enums.RoleEnum;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.RoleRepository;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final Utils utils;

    public GenericResponse<Role> getAllRoles(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Role> rolePage = roleRepository.findAll(pageable);
        return utils.generateGenericResponse(
                OK.value(),
                rolePage.getNumber(),
                rolePage.getSize(),
                rolePage.getTotalPages(),
                rolePage.getTotalElements(),
                rolePage.getContent()
        );
    }

    public void addRole(RoleEnum roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = Role.builder().name(roleName).build();
            roleRepository.save(role);
        }
    }
}
