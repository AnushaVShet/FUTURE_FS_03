package com.anvera.anvera_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.anvera.anvera_backend.entity.Product;
import com.anvera.anvera_backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @PostMapping
public Product addProduct(@RequestBody Product product) {
    return productRepository.save(product);
}
@PutMapping("/{id}")
public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {

    product.setId(id);

    return productRepository.save(product);
}

@DeleteMapping("/{id}")
public String deleteProduct(@PathVariable Integer id) {

    productRepository.deleteById(id);

    return "Product deleted successfully";
}
}
