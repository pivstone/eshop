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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private Page<Category> index(@PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC)
                                 Pageable pageable) {

        return this.categoryRepo.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Category show(@PathVariable UUID id) {
        return categoryRepo.findOne(id);
    }


    @RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
    private Page<Product> index(@PathVariable UUID id,
                                @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable) {
        return this.productRepo.findByCategoryId(id, pageable);
    }

}
