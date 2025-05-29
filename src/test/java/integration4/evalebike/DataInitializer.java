package integration4.evalebike;

import integration4.evalebike.domain.*;
import integration4.evalebike.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("test")
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepositoryTest userRepository;
    @Autowired
    private AdminRepositoryTest administratorRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) {
        if (!userRepository.existsById(3)) {
            Administrator user3 = new Administrator();
            user3.setName("Alice Johnson");
            user3.setEmail("alice.johnson@example.com");
            user3.setPassword("$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K");
            user3.setRole(Role.ADMIN);
            user3.setUserStatus(UserStatus.APPROVED);

            Company company = new Company();
            company.setName("E-Bike Corp");
            companyRepository.save(company);
            user3.setCompany(company);

            userRepository.save(user3);
            administratorRepository.save(user3);
        }

        if (!userRepository.existsById(16)) {
            Administrator user16 = new Administrator();
            user16.setName("Nora");
            user16.setEmail("nora@example.com");
            user16.setPassword("$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K");
            user16.setRole(Role.ADMIN);
            user16.setUserStatus(UserStatus.APPROVED);

            Company company = new Company();
            company.setName("E-Nursing");
            companyRepository.save(company);
            user16.setCompany(company);

            userRepository.save(user16);
            administratorRepository.save(user16);
        }
    }
}
