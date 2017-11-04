package com.pivstone.eshop.controller;

import com.pivstone.eshop.jpa.ProductRepo;
import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
import com.pivstone.eshop.resource.CategoryResource;
import com.pivstone.eshop.resource.CategoryResourceAssembler;
import com.pivstone.eshop.resource.ProductResource;
import com.pivstone.eshop.resource.ProductResourceAssembler;
import com.pivstone.eshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RestController("category")
@RequestMapping("/categories")
@ExposesResourceFor(CategoryResource.class)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private final ProductRepo productRepo;

    @Autowired
    private CategoryResourceAssembler assembler;

    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    public CategoryController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/")
    public PagedResources<CategoryResource> index(@PageableDefault Pageable pageable,
                                                  PagedResourcesAssembler<Category> assembler) {

        Page<Category> categories = this.categoryService.findAll(pageable);
        return assembler.toResource(categories, this.assembler);
    }

    @GetMapping("/{id}")
    public CategoryResource show(@PathVariable UUID id) {
        Category category = categoryService.findOne(id).orElseThrow(() -> new EntityNotFoundException("category not found"));
        return assembler.toResource(category);
    }

    @PostMapping
    private ResponseEntity<Category> create(@RequestBody Category category) {
        Category result = this.categoryService.save(category);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/{id}/products")
    public PagedResources<ProductResource> products(@PathVariable UUID id,
                                                    @PageableDefault(sort = {"id"}) Pageable pageable,
                                                    PagedResourcesAssembler<Product> assembler) {
        Page<Product> products = this.productRepo.findByCategory_Id(id, pageable);
        return assembler.toResource(products, this.productResourceAssembler);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Category> destroy(@PathVariable UUID id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    private Category update(@PathVariable UUID id, @RequestBody Category category) {
        category.setId(id);
        return this.categoryService.save(category);
    }

}
