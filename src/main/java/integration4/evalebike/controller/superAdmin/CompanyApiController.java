package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.superAdmin.dto.AddCompanyDto;
import integration4.evalebike.domain.Company;
import integration4.evalebike.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/super-admin/companies")
public class CompanyApiController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyApiController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<?> saveCompany(@RequestBody AddCompanyDto addCompanyDto) {
        Company company = new Company(
                addCompanyDto.name(),
                addCompanyDto.address(),
                addCompanyDto.email(),
                addCompanyDto.phone()
                );
        Company savedCompany = companyService.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(companyMapper.toCompanyDto(savedCompany));
    }
}
