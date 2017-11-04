package com.pivstone.eshop.services;

import com.pivstone.eshop.jpa.ProductRepo;
import com.pivstone.eshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    public Optional<Product> findOne(UUID id) {
        return repo.findById(id);
    }

    public Product save(Product entity) {
        return repo.save(entity);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }

    public Page<Product> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
