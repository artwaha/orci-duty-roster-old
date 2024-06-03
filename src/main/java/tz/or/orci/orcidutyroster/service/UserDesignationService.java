package tz.or.orci.orcidutyroster.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.UserDesignation;
import tz.or.orci.orcidutyroster.payload.request.UserDesignationDto;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.UserDesignationRepository;
import tz.or.orci.orcidutyroster.security.CustomException;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class UserDesignationService {
    private final UserDesignationRepository userDesignationRepository;
    private final ModelMapper modelMapper;
    private final Utils utils;

    public UserDesignation addUserDesignation(UserDesignationDto userDesignationDto) {
        if (userDesignationRepository.existsByName(userDesignationDto.getName()))
            throw new CustomException("User Designation with name " + userDesignationDto.getName() + " already Exists");

        UserDesignation userDesignation = modelMapper.map(userDesignationDto, UserDesignation.class);

        return userDesignationRepository.save(userDesignation);
    }

    public UserDesignation getUserCategoryById(Long userCategoryId) {
        return userDesignationRepository.findById(userCategoryId).orElseThrow(() -> new EntityNotFoundException("User Designation with Id " + userCategoryId + " not found"));
    }

    public GenericResponse<UserDesignation> getAllUserCategories(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UserDesignation> userCategoryPage = userDesignationRepository.findAll(pageable);
        return utils.generateGenericResponse(
                OK.value(),
                userCategoryPage.getNumber(),
                userCategoryPage.getSize(),
                userCategoryPage.getTotalPages(),
                userCategoryPage.getTotalElements(),
                userCategoryPage.getContent()
        );
    }

    public UserDesignation updateUserCategory(Long userDesignationId, UserDesignationDto userDesignationDto) {
        UserDesignation savedUserDesignation = userDesignationRepository.findById(userDesignationId).orElseThrow(() -> new EntityNotFoundException("User Designation with Id " + userDesignationId + " not found"));

        if (userDesignationDto.getName() != null) {
            if (userDesignationRepository.existsByName(userDesignationDto.getName()))
                throw new CustomException("User Designation with name " + userDesignationDto.getName() + " already Exists");
            savedUserDesignation.setName(userDesignationDto.getName());
        }

        return userDesignationRepository.save(savedUserDesignation);
    }
}
