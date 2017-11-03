package com.pivstone.eshop.controller;

import com.pivstone.eshop.domain.Category;
import com.pivstone.eshop.domain.Product;
import com.pivstone.eshop.repo.CategoryRepo;
import com.pivstone.eshop.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RestController("category")
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    @Autowired
    public CategoryController(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private Page<Category> index(@PageableDefault(value = 15, sort = {"name"}, direction = Sort.Direction.DESC)
                                 Pageable pageable) {

        return this.categoryRepo.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Category show(@PathVariable UUID id) {
        return categoryRepo.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    private ResponseEntity<Category> create(@RequestBody Category category) {
        Category result = this.categoryRepo.save(category);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    @RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
    private Page<Product> products(@PathVariable UUID id,
                                   @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC)
                                   Pageable pageable) {
        return this.productRepo.findByCategory_Id(id, pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<Category> destroy(@PathVariable UUID id) {
        this.categoryRepo.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private Category update(@PathVariable UUID id, @RequestBody Category category) {
        category.setId(id);
        return this.categoryRepo.save(category);
    }

}
