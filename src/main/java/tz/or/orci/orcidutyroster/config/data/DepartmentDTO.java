package tz.or.orci.orcidutyroster.config.data;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private String name;
    private List<String> workstations;
    private List<String> userDesignations;
    private List<String> shifts;
}
