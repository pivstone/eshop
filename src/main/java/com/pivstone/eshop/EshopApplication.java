package com.pivstone.eshop;

import com.pivstone.eshop.domain.Account;
import com.pivstone.eshop.domain.Category;
import com.pivstone.eshop.domain.Product;
import com.pivstone.eshop.repo.AccountRepo;
import com.pivstone.eshop.repo.CategoryRepo;
import com.pivstone.eshop.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class EshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init(ProductRepo productRepo, CategoryRepo categoryRepo, AccountRepo accountRepo) {
        return (args) -> {
            Arrays.asList("fox,rabbit,cat,dog".split(","))
                    .forEach(name -> {
                        Optional<Category> categoryOptional = categoryRepo.findByName(name);
                        if (!categoryOptional.isPresent()) {
                            Category category = new Category();
                            category.setName(name);
                            categoryRepo.save(category);
                        }
                    });

            Arrays.asList("car,bike,mobile".split(","))
                    .forEach(name -> {
                        Optional<Product> productOptional = productRepo.findByName(name);
                        if (!productOptional.isPresent()) {
                            Product product = new Product();
                            product.setName(name);
                            product.setPrice(new BigDecimal(12.20));
                            for (Category category : categoryRepo.findAll()) {
                                Category temp = new Category();
                                temp.setId(category.getId());
                                product.getCategory().add(temp);
                            }
                            productRepo.save(product);
                        }
                    });
            Optional<Account> accountOptional = accountRepo.findByUsername("admin");
            if (!accountOptional.isPresent()) {
                Account admin = new Account();
                admin.setAdmin(Boolean.TRUE);
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("password"));
                accountRepo.save(admin);
            }

        };
    }
}
