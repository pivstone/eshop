package com.pivstone.eshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivstone.eshop.EshopApplication;
import com.pivstone.eshop.jpa.CategoryRepo;
import com.pivstone.eshop.jpa.ProductRepo;
import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
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
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ProductControllerTest {
    @Autowired
    MockMvc mvc;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    private Product bike;
    private Product car;


    @Before
    public void setUp() throws Exception {
        this.productRepo.deleteAll();
        bike = new Product();
        car = new Product();
        bike.setName("bike");
        bike.setPrice(new BigDecimal(12));
        car.setName("car");
        car.setPrice(new BigDecimal(12));
        bike = productRepo.save(bike);
        car = productRepo.save(car);
    }


    @Test
    public void testShowProduct() throws Exception {
        this.mvc.perform(get("/products/" + this.bike.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.bike.getId().toString())))
                .andExpect(jsonPath("$.name", is(this.bike.getName())));

    }

    @Test
    public void testIndexProduct() throws Exception {
        this.mvc.perform(get("/products/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(this.bike.getId().toString())))
                .andExpect(jsonPath("$.content[0].name", is(this.bike.getName())))
                .andExpect(jsonPath("$.content[1].id", is(this.car.getId().toString())))
                .andExpect(jsonPath("$.content[1].name", is(this.car.getName())));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testCreateProduct() throws Exception {
        Product mobile = new Product();
        mobile.setName("mobile");
        mobile.setPrice(new BigDecimal(12));
        String productJson = json(mobile);

        this.mvc.perform(post("/products/")
                .contentType(contentType)
                .content(productJson)
                .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testDestroyProduct() throws Exception {
        Product box = new Product();
        box.setName("test2");
        box.setPrice(new BigDecimal(12));
        box = this.productRepo.save(box);
        this.mvc.perform(delete("/products/" + box.getId() + "/")
                .with(csrf()))
                .andExpect(status().isNoContent());
        assertFalse(this.productRepo.exists(box.getId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setName("test2");
        product.setPrice(new BigDecimal(12));
        product = this.productRepo.save(product);
        product.setName("test3");
        String productJson = json(product);
        this.mvc.perform(put("/products/" + product.getId() + "/")
                .contentType(contentType)
                .content(productJson)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.createdAt",notNullValue()))
                .andExpect(jsonPath("$.name", is(product.getName())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testCreateProductWithCategoryId() throws Exception {
        Category category = new Category();
        category.setName("test");
        category = this.categoryRepo.save(category);
        Product product = new Product();
        product.setName("test2");
        product.setPrice(new BigDecimal(12));
        product.getCategoriesIdList().add(category.getId());
        String productJson = json(product);
        ObjectMapper mapper = new ObjectMapper();
        Map data = mapper.readValue(productJson, Map.class);
        // CategoriesIdList exclude at the json
        // so need add it into json string manually
        data.put("categoriesIdList",product.getCategoriesIdList());
        productJson = json(data);
        this.mvc.perform(post("/products/")
                .contentType(contentType)
                .content(productJson)
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.category[0].id", is(category.getId().toString())));

    }

    protected String json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }
}