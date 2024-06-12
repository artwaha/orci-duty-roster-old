package tz.or.orci.orcidutyroster.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "departments")
public class DepartmentConfigProperties {
    private List<DepartmentDTO> departments;
}
