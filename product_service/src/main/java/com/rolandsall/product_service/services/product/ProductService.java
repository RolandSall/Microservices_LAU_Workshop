package com.rolandsall.product_service.services.product;

import com.rolandsall.product_service.daos.ProductRepository;
import com.rolandsall.product_service.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements IProductService {


    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        UUID productID = UUID.randomUUID();
        product.setId(productID);
        Product productAdded = productRepository.save(product);
        return productAdded;
    }

    @Override
    public Product getProductById(UUID productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.get();
    }
}
