package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchByName(@Param("query") String query);

    @Query(value = "SELECT * FROM products ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Product> findRandomProducts(@Param("limit") int limit);
}