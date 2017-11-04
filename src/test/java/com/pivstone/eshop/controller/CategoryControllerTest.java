package com.pivstone.eshop.controller;

import com.pivstone.eshop.EshopApplication;
import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.jpa.CategoryRepo;
import com.pivstone.eshop.jpa.ProductRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    private HttpMessageConverter messageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.messageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.messageConverter);
    }

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
                .andExpect(jsonPath("$.name", is(this.color.getName())));

    }

    @Test
    public void testIndexCategory() throws Exception {
        this.mvc.perform(get("/categories/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(this.color.getId().toString())))
                .andExpect(jsonPath("$.content[0].name", is(this.color.getName())))
                .andExpect(jsonPath("$.content[1].id", is(this.band.getId().toString())))
                .andExpect(jsonPath("$.content[1].name", is(this.band.getName())));

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
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
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void testDestroyCategory() throws Exception {
        Category category = new Category();
        category.setName("test2");
        category = this.categoryRepo.save(category);
        StringBuilder url = new StringBuilder();
        url.append("/categories/");
        url.append(category.getId());
        url.append("/");
        this.mvc.perform(delete(url.toString())
                .with(csrf())).
                andExpect(status().isNoContent());
        assertFalse(this.categoryRepo.exists(category.getId()));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void testUpdateCategory() throws Exception {
        Category category = new Category();
        category.setName("test2");

        category = this.categoryRepo.save(category);
        category.setName("test3");
        String categoryJson = json(category);
        StringBuilder url = new StringBuilder();
        url.append("/categories/");
        url.append(category.getId());
        url.append("/");
        this.mvc.perform(put(url.toString())
                .contentType(contentType)
                .content(categoryJson)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(category.getName())));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.messageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}