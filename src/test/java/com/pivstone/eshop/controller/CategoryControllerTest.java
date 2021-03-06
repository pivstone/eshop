package com.pivstone.eshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivstone.eshop.EshopApplication;
import com.pivstone.eshop.jpa.CategoryRepo;
import com.pivstone.eshop.jpa.ProductRepo;
import com.pivstone.eshop.model.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class CategoryControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductRepo productRepo;
    private Category color;
    private Category band;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        this.productRepo.deleteAll();
        this.categoryRepo.deleteAll();
        this.color = new Category();
        this.band = new Category();
        color.setName("white");
        band.setName("nike");
        color = categoryRepo.save(color);
        band = categoryRepo.save(band);
    }

    @Test
    public void testShowCategory() throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("/categories/");
        url.append(this.color.getId());
        url.append("/");
        this.mvc.perform(get(url.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.color.getId().toString())))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.name", is(this.color.getName())));

    }

    @Test
    public void testIndexCategory() throws Exception {
        this.mvc.perform(get("/categories/?sort=name,DESC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(this.color.getId().toString())))
                .andExpect(jsonPath("$.content[0].name", is(this.color.getName())))
                .andExpect(jsonPath("$.content[1].id", is(this.band.getId().toString())))
                .andExpect(jsonPath("$.content[1].name", is(this.band.getName())));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setName("test");
        String categoryJson = json(category);

        this.mvc.perform(post("/categories/")
                .contentType(contentType)
                .content(categoryJson)
                .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testDestroyCategory() throws Exception {
        Category category = new Category();
        category.setName("test2");
        category = this.categoryRepo.save(category);
        this.mvc.perform(delete("/categories/" + category.getId() + "/")
                .with(csrf())).
                andExpect(status().isNoContent());
        assertFalse(this.categoryRepo.exists(category.getId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testUpdateCategory() throws Exception {
        Category category = new Category();
        category.setName("test2");

        category = this.categoryRepo.save(category);
        category.setName("test3");
        String categoryJson = json(category);
        this.mvc.perform(put("/categories/" + category.getId() + "/")
                .contentType(contentType)
                .content(categoryJson)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.name", is(category.getName())));
    }

    @Test
    public void testHeadCategory() throws Exception {
        Category category = new Category();
        category.setName("test2");

        category = this.categoryRepo.save(category);
        this.mvc.perform(head("/categories/" + category.getId() + "/")).
                andExpect(status().isNoContent());
    }

    @Test
    public void testHeadCategoryNotExists() throws Exception {

        this.mvc.perform(head("/categories/" + UUID.randomUUID() + "/")).
                andExpect(status().isNotFound());
    }

    protected String json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

}