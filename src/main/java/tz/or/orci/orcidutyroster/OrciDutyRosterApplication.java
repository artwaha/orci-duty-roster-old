package tz.or.orci.orcidutyroster;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tz.or.orci.orcidutyroster.service.DataInitService;

@SpringBootApplication
@RequiredArgsConstructor
public class OrciDutyRosterApplication implements ApplicationRunner {
    private final DataInitService dataInitService;

    public static void main(String[] args) {
        SpringApplication.run(OrciDutyRosterApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataInitService.addDefaultRoles();
        dataInitService.addDefaultUsers();
    }
}
