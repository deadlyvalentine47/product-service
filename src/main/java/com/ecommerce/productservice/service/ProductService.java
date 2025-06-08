package com.ecommerce.productservice.service;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with name " + product.getName() + " already exists");
        }
        product.setName(product.getName().trim());
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }
        return productRepository.searchByName(query.trim());
    }

    public List<Product> getRandomProducts(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }
        return productRepository.findRandomProducts(limit);
    }

    public Product updateProductStock(Long id, int newStock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        product.setStock(newStock);
        return productRepository.save(product);
    }
}