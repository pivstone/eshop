package com.pivstone.eshop;

import com.pivstone.eshop.jpa.AccountRepo;
import com.pivstone.eshop.jpa.CategoryRepo;
import com.pivstone.eshop.jpa.ProductRepo;
import com.pivstone.eshop.model.Account;
import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class EshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder();
    }

    @Bean
    CommandLineRunner init(ProductRepo productRepo, CategoryRepo categoryRepo, AccountRepo accountRepo) {
        return (args) -> {
            Arrays.asList("fox,rabbit,cat,dog".split(","))
                    .forEach(name -> {
                        if (!categoryRepo.existsByName(name)) {
                            Category category = new Category();
                            category.setName(name);
                            categoryRepo.save(category);
                        }
                    });

            Arrays.asList("car,bike,mobile".split(","))
                    .forEach(name -> {
                        if (!productRepo.existsByName(name)) {
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
