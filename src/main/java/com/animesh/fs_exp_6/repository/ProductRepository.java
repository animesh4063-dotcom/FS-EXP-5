package com.animesh.fs_exp_6.repository;

import com.animesh.fs_exp_6.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Requirement C: Custom JPQL Query
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryNameCustom(@Param("categoryName") String categoryName);

    // Requirement C: Filtering by price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Requirement C: Pagination (Splitting large data into pages)
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}