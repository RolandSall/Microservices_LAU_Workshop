package com.rolandsall.company_service.controllers.company;

import com.rolandsall.company_service.entities.Company;
import com.rolandsall.company_service.services.CompanyService;
import com.rolandsall.company_service.services.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CompanyController {

    ICompanyService companyService;

    @Autowired
    public CompanyController(ICompanyService companyService) {
        this.companyService =  companyService;
    }


        @GetMapping("/companies")
    public ResponseEntity findAllCompanies() {
        try {
            List<Company> companyList = companyService.getAllCompanies();
            List<CompanyApiResponse> response = buildListOfResponse(companyList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // company Service
    // Product Service

    // Company Service Wants to Talk to the Product Service Using The BUS.

    @GetMapping("/companies/{companyId}")
    public ResponseEntity findCompanyById(@PathVariable("companyId") UUID companyId) {
        try {
            Company company = companyService.getCompanyById(companyId);
            CompanyApiResponse response = buildResponse(company);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/companies/create")
    public ResponseEntity createCompany(@RequestBody CompanyApiRequest request) {
        try {
            Company company = companyService.save(buildCompanyFromRequest(request));
            CompanyApiResponse response = buildResponse(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @DeleteMapping("/companies/{companyId}")
    public ResponseEntity deleteCompany(@PathVariable("companyId") UUID companyId) {
        try {
            System.out.println(companyId.toString());
            UUID companyIdDeleted = companyService.deleteCompany(companyId);
            return ResponseEntity.status(HttpStatus.OK).body(companyIdDeleted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company Not Found");
        }
    }

    private Company buildCompanyFromRequest(CompanyApiRequest request) {
        return new Company().builder()
                .companyName(request.getCompanyName())
                .price(request.getPrice())
                .build();
    }


    private List<CompanyApiResponse> buildListOfResponse(List<Company> companyList) {
        List<CompanyApiResponse> responseList = new ArrayList<>();
        for (Company company : companyList) {
            responseList.add(buildResponse(company));
        }
        return responseList;
    }

    private CompanyApiResponse buildResponse(Company company) {
        return new CompanyApiResponse().builder()
                .companyName(company.getCompanyName())
                .id(company.getId())
                .price(company.getPrice())
                .build();
    }

}
