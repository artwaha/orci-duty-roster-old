package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Department;
import tz.or.orci.orcidutyroster.model.entities.Designation;
import tz.or.orci.orcidutyroster.payload.request.UserCategoryDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.repository.UserCategoryRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;
    private final Utils utils;

    public Designation addUserCategory(UserCategoryDto userCategoryDto) {
        Department department = departmentRepository.findById(userCategoryDto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department with Id " + userCategoryDto.getDepartmentId() + " not found"));

        Optional<Designation> userCategoryOptional = userCategoryRepository.findByNameIgnoreCase(userCategoryDto.getName());
        if (userCategoryOptional.isPresent())
            throw new CustomException("User Category with name " + userCategoryDto.getName() + " Already Exists");

        Designation designation = modelMapper.map(userCategoryDto, Designation.class);
        designation.setDepartment(department);

        return userCategoryRepository.save(designation);
    }

    public Designation getUserCategoryById(Long userCategoryId) {
        return userCategoryRepository.findById(userCategoryId).orElseThrow(() -> new EntityNotFoundException("User Category with Id " + userCategoryId + " not found"));
    }

    public GenericResponse<Designation> getAllUserCategories(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Designation> userCategoryPage = userCategoryRepository.findAll(pageable);
        return utils.generateGenericResponse(
                OK.value(),
                userCategoryPage.getNumber(),
                userCategoryPage.getSize(),
                userCategoryPage.getTotalPages(),
                userCategoryPage.getTotalElements(),
                userCategoryPage.getContent()
        );
    }

    public GenericResponse<Designation> getAllUserCategoriesByDepartment(Long departmentId, int pageNumber, int pageSize) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotFoundException("Department with Id " + departmentId + " not found"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Designation> userCategoryPage = userCategoryRepository.findByDepartment(department, pageable);
        return utils.generateGenericResponse(
                OK.value(),
                userCategoryPage.getNumber(),
                userCategoryPage.getSize(),
                userCategoryPage.getTotalPages(),
                userCategoryPage.getTotalElements(),
                userCategoryPage.getContent()
        );
    }

    public Designation updateUserCategory(Long userCategoryId, UserCategoryDto userCategoryDto) {
        Designation savedDesignation = userCategoryRepository.findById(userCategoryId).orElseThrow(() -> new EntityNotFoundException("User Category with Id " + userCategoryId + " not found"));

        if (userCategoryDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(userCategoryDto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("Department with Id " + userCategoryDto.getDepartmentId() + " not found"));
            savedDesignation.setDepartment(department);
        }

        if (userCategoryDto.getName() != null) {
            Optional<Designation> userCategoryOptional = userCategoryRepository.findByNameIgnoreCase(userCategoryDto.getName());
            if (userCategoryOptional.isPresent())
                throw new CustomException("User Category with name " + userCategoryDto.getName() + " Already Exists");
            savedDesignation.setName(userCategoryDto.getName());
        }

        return userCategoryRepository.save(savedDesignation);
    }
}
