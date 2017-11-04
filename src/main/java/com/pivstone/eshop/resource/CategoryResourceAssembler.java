package com.pivstone.eshop.resource;

import com.pivstone.eshop.controller.CategoryController;
import com.pivstone.eshop.model.Category;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/4.
 */
@Component
public class CategoryResourceAssembler extends ResourceAssemblerSupport<Category, CategoryResource> {
    public CategoryResourceAssembler() {
        super(CategoryController.class, CategoryResource.class);
    }

    @Override
    public CategoryResource toResource(Category entity) {
        return new CategoryResource(entity);
    }

    @Override
    public List<CategoryResource> toResources(Iterable<? extends Category> categories) {
        List<CategoryResource> resources = new ArrayList<>();
        for (Category category : categories) {
            resources.add(new CategoryResource(category));
        }
        return resources;
    }


}
