package com.pivstone.eshop.resource;

import com.pivstone.eshop.controller.CategoryController;
import com.pivstone.eshop.model.Category;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
public class CategoryResource extends Resource<Category> {

    public CategoryResource(Category category) {
        super(category);
        this.add(linkTo(methodOn(CategoryController.class).show(category.getId())).withSelfRel());
        this.add(linkTo(methodOn(CategoryController.class).index(null)).withRel("list"));
        this.add(linkTo(methodOn(CategoryController.class).products(category.getId(), null, null)).withRel("products"));
    }
}
