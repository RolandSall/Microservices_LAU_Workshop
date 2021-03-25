package com.rolandsall.distributor_service;

import com.rolandsall.distributor_service.entity.Company;
import com.rolandsall.distributor_service.entity.CompanyProduct;
import com.rolandsall.distributor_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class TestController {

    private WebClient webClient;

    @Autowired
    public TestController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/tryConnection")
    public ResponseEntity tryCon() {
           webClient.get().uri("http://COMPANY-SERVICE/companies/168c4867-1675-479c-bef0-808a24ac383e")
                .retrieve()
                .bodyToMono(Company.class)
                .block();
        return ResponseEntity.status(HttpStatus.CREATED).body("companyProductResponse");

    }

}
