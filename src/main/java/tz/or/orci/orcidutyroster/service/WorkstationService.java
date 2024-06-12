package tz.or.orci.orcidutyroster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.Workstation;
import tz.or.orci.orcidutyroster.payload.response.GenericResponse;
import tz.or.orci.orcidutyroster.repository.DepartmentRepository;
import tz.or.orci.orcidutyroster.repository.WorkstationRepository;

@Service
@RequiredArgsConstructor
public class WorkstationService {
    private final DepartmentRepository departmentRepository;
    private final WorkstationRepository workstationRepository;
    private final Utils utils;

    public GenericResponse<Workstation> getAllWorkstations(int pageNumber, int pageSize) {
        return null;
    }

//    public void addWorkstation(WorkstationEnum workstationName, DepartmentEnum departmentName) {
//        if (!workstationRepository.existsByName(workstationName) && departmentRepository.existsByName(departmentName)) {
//            Department department = departmentRepository.findDepartmentByName(departmentName);
//            Workstation workstation = Workstation.builder().name(workstationName).department(department).build();
//            workstationRepository.save(workstation);
//        }
//    }
//
//    public GenericResponse<Workstation> getAllWorkstations(int pageNumber, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<Workstation> workstationPage = workstationRepository.findAll(pageable);
//        return utils.generateGenericResponse(
//                OK.value(),
//                workstationPage.getNumber(),
//                workstationPage.getSize(),
//                workstationPage.getTotalPages(),
//                workstationPage.getTotalElements(),
//                workstationPage.getContent()
//        );
//    }

}
