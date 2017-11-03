package com.pivstone.eshop.controller;

import com.pivstone.eshop.EshopApplication;
import com.pivstone.eshop.domain.Product;
import com.pivstone.eshop.repo.ProductRepo;
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
public class ProductControllerTest {
    @Autowired
    MockMvc mvc;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private HttpMessageConverter messageConverter;

    @Autowired
    private ProductRepo productRepo;
    private Product bike;
    private Product car;

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
        bike = new Product();
        car = new Product();
        bike.setName("bike");
        car.setName("car");
        bike = productRepo.save(bike);
        car = productRepo.save(car);
    }


    @Test
    public void testShowProduct() throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("/products/");
        url.append(this.bike.getId());
        url.append("/");
        this.mvc.perform(get(url.toString()))
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
                .andExpect(jsonPath("$.content[0].id", is(this.car.getId().toString())))
                .andExpect(jsonPath("$.content[0].name", is(this.car.getName())))
                .andExpect(jsonPath("$.content[1].id", is(this.bike.getId().toString())))
                .andExpect(jsonPath("$.content[1].name", is(this.bike.getName())));

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void testCreateProduct() throws Exception {
        Product mobile = new Product();
        mobile.setName("mobile");
        String productJson = json(mobile);

        this.mvc.perform(post("/products/")
                .contentType(contentType)
                .content(productJson)
                .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void testDestroyProduct() throws Exception {
        Product box = new Product();
        box.setName("test2");
        box = this.productRepo.save(box);
        StringBuilder url = new StringBuilder();
        url.append("/products/");
        url.append(box.getId());
        url.append("/");
        this.mvc.perform(delete(url.toString())
                .with(csrf()))
                .andExpect(status().isNoContent());
        assertFalse(this.productRepo.exists(box.getId()));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setName("test2");

        product = this.productRepo.save(product);
        product.setName("test3");
        String productJson = json(product);
        StringBuilder url = new StringBuilder();
        url.append("/products/");
        url.append(product.getId());
        url.append("/");
        this.mvc.perform(put(url.toString())
                .contentType(contentType)
                .content(productJson)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(product.getName())));
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.messageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}