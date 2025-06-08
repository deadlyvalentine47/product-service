package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(productService.searchProducts(query));
    }

    @GetMapping("/random")
    public ResponseEntity<List<Product>> getRandomProducts(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(productService.getRandomProducts(limit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductStock(@PathVariable Long id, @RequestBody Map<String, Integer> stockUpdate) {
        if (!stockUpdate.containsKey("stock")) {
            throw new IllegalArgumentException("Stock value is required");
        }
        Product updatedProduct = productService.updateProductStock(id, stockUpdate.get("stock"));
        return ResponseEntity.ok(updatedProduct);
    }
}