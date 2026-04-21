package com.animesh.fs_exp_6;

import com.animesh.fs_exp_6.entity.Category;
import com.animesh.fs_exp_6.entity.Product;
import com.animesh.fs_exp_6.repository.CategoryRepository;
import com.animesh.fs_exp_6.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List; // Using this instead of Arrays!

@SpringBootApplication
public class FsExp6Application {

    public static void main(String[] args) {
        SpringApplication.run(FsExp6Application.class, args);
    }

    @Bean
    public CommandLineRunner testQueries(CategoryRepository categoryRepo, ProductRepository productRepo) {
        return args -> {
            System.out.println("\n=== INSERTING TEST DATA ===");
            
            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryRepo.save(electronics);

            Product p1 = new Product(); p1.setName("Laptop"); p1.setPrice(1200.00); p1.setCategory(electronics);
            Product p2 = new Product(); p2.setName("Mouse"); p2.setPrice(25.00); p2.setCategory(electronics);
            Product p3 = new Product(); p3.setName("Keyboard"); p3.setPrice(75.00); p3.setCategory(electronics);
            
            // Replaced Arrays.asList with List.of
            productRepo.saveAll(List.of(p1, p2, p3));

            System.out.println("\n=== TESTING CUSTOM JPQL (Exact Category) ===");
            productRepo.findByCategoryNameCustom("Electronics")
                    .forEach(p -> System.out.println("Found: " + p.getName()));

            System.out.println("\n=== TESTING FILTERING (Price Between $20 and $80) ===");
            productRepo.findByPriceBetween(20.0, 80.0)
                    .forEach(p -> System.out.println("Found: " + p.getName() + " - $" + p.getPrice()));

            System.out.println("\n=== TESTING PAGINATION (Page 0, Size 2) ===");
            Page<Product> page = productRepo.findByCategoryId(electronics.getId(), PageRequest.of(0, 2));
            page.getContent().forEach(p -> System.out.println("Page 1 Item: " + p.getName()));
            
            System.out.println("\n=== EXPERIMENT 6 COMPLETE ===\n");
        };
    }
}