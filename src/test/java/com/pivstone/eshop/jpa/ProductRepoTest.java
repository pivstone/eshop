package com.pivstone.eshop.jpa;

import com.pivstone.eshop.model.Category;
import com.pivstone.eshop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    private UUID categoryId;
    private Category category;


    @Before
    public void setUp() {
        category = new Category();
        category.setName("white");
        Category band = new Category();
        band.setName("Nike~");
        band = categoryRepo.save(band);
        category = categoryRepo.save(category);
        this.categoryId = category.getId();

        Category temp = new Category();
        temp.setId(category.getId());
        Product shoes = new Product();
        shoes.getCategory().add(temp);
        Category temp2 = new Category();
        temp2.setId(band.getId());
        shoes.getCategory().add(temp2);

        shoes.setName("Fizzy");
        BigDecimal price = new BigDecimal(10.2);
        shoes.setPrice(price);

        shoes = productRepo.save(shoes);
    }

    @Test
    public void testFindProductByCategoryId() throws Exception {
        Collection<Product> products = productRepo.findByCategory_Id(this.categoryId);
        assertEquals(products.size(), 1);

    }
}
