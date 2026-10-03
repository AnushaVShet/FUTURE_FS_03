package com.anvera.anvera_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.anvera.anvera_backend.entity.Product;
import com.anvera.anvera_backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/admin/protected/products")
public class AdminProductController {

    private final ProductRepository productRepository;

    public AdminProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products for admin
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Add product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Update product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Integer id,
            @RequestBody Product product) {

        product.setId(id);

        return productRepository.save(product);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Integer id) {

        productRepository.deleteById(id);

        return "Product deleted successfully";
    }
}
