package com.pivstone.eshop.services;

import com.pivstone.eshop.jpa.CategoryRepo;
import com.pivstone.eshop.model.Category;
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
public class CategoryService {
    @Autowired
    private CategoryRepo repo;

    public Optional<Category> findOne(UUID id) {
        return repo.findById(id);
    }

    public Category save(Category category) {
        return repo.save(category);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }

    public Page<Category> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
