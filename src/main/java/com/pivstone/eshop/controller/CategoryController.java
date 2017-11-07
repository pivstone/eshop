package com.pivstone.eshop.controller;

import com.pivstone.eshop.jpa.ProductRepo;
import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
import com.pivstone.eshop.resource.CategoryResource;
import com.pivstone.eshop.resource.ProductResource;
import com.pivstone.eshop.resource.ProductResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RestController("category")
@RequestMapping(path = "/categories", produces = "application/json; charset=UTF-8")
@ExposesResourceFor(CategoryResource.class)
public class CategoryController extends AbstractController<Category> {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    /**
     * List of all products have this category
     *
     * @param id        Category Id
     * @param pageable  Page parameters
     * @param assembler PagedResourcesAssembler
     * @return Paged products
     */
    @GetMapping("/{id}/products")
    public PagedResources<ProductResource> products(@PathVariable UUID id,
                                                    @PageableDefault(sort = {"id"}) Pageable pageable,
                                                    PagedResourcesAssembler<Product> assembler) {
        Category category = super.getInstance(id);
        Page<Product> products = this.productRepo.findByCategory_Id(category.getId(), pageable);
        return assembler.toResource(products, this.productResourceAssembler);
    }


}
