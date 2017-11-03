package com.pivstone.eshop.repo;

import com.pivstone.eshop.domain.Category;
import com.pivstone.eshop.domain.Product;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepo productRepo;

    private UUID categoryId;
    @Before
    public void setUp() {
        Category color = new Category();
        color.setName("white");
        Category band = new Category();
        band.setName("Nike~");
        band = entityManager.persist(band);
        color = entityManager.persist(color);
        this.categoryId = band.getId();
        Product shoes = new Product();
        Set<Category> categories = new HashSet<>();
        categories.add(band);
        categories.add(color);
        shoes.setCategory(categories);
        shoes.setName("Fizzy");
        BigDecimal price = new BigDecimal(10.2);
        shoes.setPrice(price);

        entityManager.persist(shoes);
    }

    @Test
    public void testFindProductByCategoryId() throws Exception {
        Collection<Product> products = productRepo.findByCategoryId(this.categoryId);
        assertThat(products.size() == 1);

    }
}
