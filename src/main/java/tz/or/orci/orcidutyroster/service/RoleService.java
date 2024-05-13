package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Role;
import tz.or.orci.orcidutyroster.payload.request.RoleDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.RoleRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import java.util.Optional;

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

    public Role updateRole(Long roleId, RoleDto roleDto) {
        Role savedRole = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role with Id " + roleId + " not found"));

        Optional<Role> roleOptional = roleRepository.findByDescriptionIgnoreCase(roleDto.getDescription());
        if (roleOptional.isPresent()) {
            throw new CustomException("Role with Description " + roleDto.getDescription() + " already Exists");
        }
        savedRole.setDescription(roleDto.getDescription());
        return roleRepository.save(savedRole);
    }
}
