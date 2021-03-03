package com.rolandsall.company_service.controllers;

import com.rolandsall.company_service.entities.Company;
import com.rolandsall.company_service.services.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyController {

    ICompanyService companyService;

    @Autowired
    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
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

    @PostMapping("/create")
    public ResponseEntity createCompany(@RequestBody CompanyApiRequest request) {
        try {
            Company company = companyService.save(buildCompanyFromRequest(request));
            CompanyApiResponse response = buildResponse(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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