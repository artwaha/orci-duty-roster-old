package tz.or.orci.orcidutyroster.config.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DepartmentConfig implements ApplicationRunner {
    private final DepartmentConfigProperties departmentConfigProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Department => " + departmentConfigProperties.toString());
//        departmentConfigProperties.getDepartments().forEach(department -> {
//            System.out.println("Department => " + department);
//        });
    }
}
